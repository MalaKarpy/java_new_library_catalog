import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Book firstBook = new Book("math", "MTH");
    Book secondBook = new Book("math", "MTH");
    assertTrue(firstBook.equals(secondBook));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Book myBook = new Book("math", "MTH");
    myBook.save();

    Book savedBook = Book.all().get(0);
    assertTrue(savedBook.equals(myBook));
  }

  @Test
  public void save_assignsIdToObject() {
    Book myBook = new Book("math", "MTH");
    myBook.save();
    Book savedBook = Book.all().get(0);
    assertEquals(myBook.getId(), savedBook.getId());
  }

  @Test
  public void find_findsBookInDatabase_true() {
    Book myBook = new Book("math", "MTH");
    myBook.save();
    Book savedBook = Book.find(myBook.getId());
    assertTrue(myBook.equals(savedBook));
  }

  @Test
  public void updateBookTitle() {
    Book myBook = new Book("math", "MTH");
    myBook.save();
    myBook.update("science");
    assertTrue(Book.all().get(0).getTitle().equals("science"));
  }

  @Test
    public void deleteBook() {
    Book myFirstBook = new Book("Biology","Bio");
    myFirstBook.save();
    Book mySecondBook = new Book("Maths","MTH");
    mySecondBook.save();
    mySecondBook.delete();
    assertTrue(Book.all().get(0).equals(myFirstBook));
  }

  @Test
  public void getAuthor_returnsAllAuthors_List() {
    Book myBook = new Book("math", "MTH");
    myBook.save();

    Author myAuthor = new Author("You");
    myAuthor.save();

    myBook.addAuthor(myAuthor);
    List savedAuthors = myBook.getAuthors();
    assertEquals(savedAuthors.size(), 1);

  }

  @Test
  public void make_copies() {
    Book myBook = new Book("math", "MTH");
    myBook.save();

    myBook.makeCopies(3);
    List<Copy> savedCopies = myBook.getCopies();
    assertEquals(savedCopies.size(), 3);

  }

  

}
