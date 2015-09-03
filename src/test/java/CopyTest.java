import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class CopyTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Copy.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfCopyIsbnAretheSame() {
    Copy firstCopy = new Copy("Mat12",2);
    Copy secondCopy = new Copy("Mat12", 2);
    assertTrue(firstCopy.equals(secondCopy));
  }

  @Test
  public void save_savesObjectIntoDatabase() {
    Copy myCopy = new Copy("Mat12",2);
    myCopy.save();

    Copy savedCopy = Copy.all().get(0);
    assertTrue(savedCopy.equals(myCopy));
  }

  @Test
  public void save_assignsIdToObject() {
    Copy myCopy = new Copy("Mat12",2);
    myCopy.save();
    Copy savedCopy = Copy.all().get(0);
    assertEquals(myCopy.getId(), savedCopy.getId());
  }

  @Test
  public void find_Object() {
    Copy myCopy = new Copy("Mat12",2);
    myCopy.save();
    Copy savedCopy = Copy.find(myCopy.getId());
    assertEquals(myCopy.getId(), savedCopy.getId());
  }

  @Test
  public void delete_Object() {
    Copy myCopy = new Copy("Mat12",2);
    myCopy.save();
    myCopy.delete();
    assertEquals(Copy.all().size(), 0);
  }



}
