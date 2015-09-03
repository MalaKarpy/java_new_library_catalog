import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Book {
  private int id;
  private String title;
  private String book_isbn;


  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }


  public Book(String title, String bookIsbn) {
    this.title = title;
    this.book_isbn = bookIsbn;

  }

  @Override
  public boolean equals(Object otherBook){
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
             this.getId() == newBook.getId();
  }
}

  public static List<Book> all() {
    String sql = "SELECT * FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Book.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title, book_isbn ) VALUES (:title,:book_isbn)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", title)
        .addParameter("book_isbn", book_isbn)
        .executeUpdate()
        .getKey();
    }
  }

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books where id=:id";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void update(String title) {
    this.title = title;
    try(Connection con = DB.sql2o.open()) {

      String sql = "UPDATE books SET title = :title WHERE id = :id";
      con.createQuery(sql)
        .addParameter("title", title)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM books WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void addAuthor(Author author) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (author_id, book_id) VALUES (:author_id, :book_id)";
      con.createQuery(sql)
      .addParameter("author_id", author.getId())
      .addParameter("book_id", this.getId())
      .executeUpdate();
    }
  }

  public List<Author> getAuthors() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT authors.* FROM books JOIN books_authors ON (books_authors.book_id = books.id) JOIN authors ON (books_authors.author_id = authors.id) WHERE book_id = :book_id";
      List<Author> authors = con.createQuery(sql)
      .addParameter("book_id", this.getId())
      .executeAndFetch(Author.class);
      return authors;

    }

  }

  public void makeCopies(int noOfCopies) {
    //String copyNum = this.boo;
    for (int i=1; i <= noOfCopies; i++) {
      String copyIsbn = book_isbn + "c" + i;
      //String copyName = "newCopy"+i;
      Copy newCopy = new Copy(copyIsbn, this.id);
      newCopy.save();//concatenation not sure
    }
  }

  public List<Copy> getCopies() {
    try(Connection con = DB.sql2o.open()) {

      String sql = "SELECT * FROM copies where book_id = :id";
      List<Copy> copies = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetch(Copy.class);
        return copies;
    }
  }


}
