import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith ( Suite.class )
@SuiteClasses ( { AccessModificatorTest.class, ObfuscationFactoryTest.class,
        PathsExtractorTest.class, PrivatePublicMatcherTest.class,
        SuffixFilterTest.class } )
public class RunAllTests {

}
