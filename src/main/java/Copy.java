import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Copy {
  private int id;
  private String copy_isbn;
  private int book_id;




  public Copy(String copy_isbn, int book_id) {
  this.copy_isbn = copy_isbn;
  this.book_id = book_id;
  }

  public int getId() {
    return id;
  }

  public String getCopyIsbn() {
    return copy_isbn;
  }

  public int getBookId() {
    return book_id;
  }

  @Override
  public boolean equals(Object otherCopy){
    if (!(otherCopy instanceof Copy)) {
      return false;
    } else {
      Copy newCopy = (Copy) otherCopy;
      return this.getCopyIsbn().equals(newCopy.getCopyIsbn()) &&
             this.getId() == newCopy.getId();
  }
}

  public static List<Copy> all() {
    String sql = "SELECT * FROM copies";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Copy.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO copies (copy_isbn, book_id) VALUES (:copy_isbn, :book_id)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("copy_isbn", copy_isbn)
        .addParameter("book_id", book_id)
        .executeUpdate()
        .getKey();
    }
  }

  public static Copy find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM copies where id=:id";
      Copy book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Copy.class);
      return book;
    }
  }


  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM copies WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public static List<Copy> availableCopies(int copy_id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM copies where id != :copy_id";
      List<Copy> copies = con.createQuery(sql)
      .addParameter("copy_id", copy_id)
      .executeAndFetch(Copy.class);
      return copies;
    }


  }

  // public void addAuthor(Author author) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "INSERT INTO copies_authors (author_id, book_id) VALUES (:author_id, :book_id)";
  //     con.createQuery(sql)
  //     .addParameter("author_id", author.getId())
  //     .addParameter("book_id", this.getId())
  //     .executeUpdate();
  //   }
  // }

//   public List<Author> getAuthors() {
//     try(Connection con = DB.sql2o.open()) {
//       String sql = "SELECT authors.* FROM copies JOIN copies_authors ON (copies_authors.book_id = copies.id) JOIN authors ON (copies_authors.author_id = authors.id) WHERE book_id = :book_id";
//       List<Author> authors = con.createQuery(sql)
//       .addParameter("book_id", this.getId())
//       .executeAndFetch(Author.class);
//       return authors;
//
//     }
//
//   }
}
