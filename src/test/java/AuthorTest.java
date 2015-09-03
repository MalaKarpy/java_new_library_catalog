import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Author firstAuthor = new Author("Tessa");
    Author secondAuthor = new Author("Tessa");
    assertTrue(firstAuthor.equals(secondAuthor));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Author myAuthor = new Author("Tessa");
    myAuthor.save();

    Author savedAuthor = Author.all().get(0);
    assertTrue(savedAuthor.equals(myAuthor));
  }

  @Test
  public void save_assignsIdToObject() {
    Author myAuthor = new Author("Tessa");
    myAuthor.save();
    Author savedAuthor = Author.all().get(0);
    assertEquals(myAuthor.getId(), savedAuthor.getId());
  }

  @Test
  public void find_findsAuthorInDatabase_true() {
    Author myAuthor = new Author("Alex");
    myAuthor.save();
    Author savedAuthor = Author.find(myAuthor.getId());
    assertTrue(myAuthor.equals(savedAuthor));
  }

  @Test
  public void updateAuthorName() {
    Author myAuthor = new Author("Alex");
    myAuthor.save();
    myAuthor.update("Bert");
     //System.out.println(myAuthor.getName());
    assertTrue(Author.all().get(0).getName().equals("Bert"));
  }

  @Test
  public void deleteAuthor() {
    Author myFirstAuthor = new Author("Bert");
    myFirstAuthor.save();
    Author mySecondAuthor = new Author("Tessa");
    mySecondAuthor.save();
    mySecondAuthor.delete();
    assertTrue(Author.all().get(0).equals(myFirstAuthor));
  }

  @Test
  public void getBooks_returnsAllBooks_List() {
    Book myBook = new Book("math","MTH1");
    myBook.save();

    Author myAuthor = new Author("You");
    myAuthor.save();

    myAuthor.addBook(myBook);
    List savedBooks = myAuthor.getBooks();
    assertEquals(savedBooks.size(), 1);

   }


}
