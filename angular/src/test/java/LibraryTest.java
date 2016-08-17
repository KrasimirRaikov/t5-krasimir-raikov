import org.junit.Test;
import static org.junit.Assert.*;

/*
 * @author Krasimir Raikov (raikov.krasimir@gmail.com)
 */
public class LibraryTest {
    @Test public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
    }
}
