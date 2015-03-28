import org.junit.Test;
import parser.CompilationUnitInitiator;

public class CompilationUnitInitiatorTest {

    @Test
    public void test () {
        CompilationUnitInitiator cuinit = new CompilationUnitInitiator();
        cuinit.injectionAttempt();
    }
}
