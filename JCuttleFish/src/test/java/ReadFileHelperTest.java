import org.junit.Test;
import util.ReadFileHelper;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertNotNull;

public class ReadFileHelperTest {

    @Test
    public void test () {
        String result = null;
        try {
            result = ReadFileHelper.readFile( "C:\\test\\JCuttleFish\\src\\main\\java\\extractor\\PathsExtractor.java",
                    Charset.forName( "UTF-8" ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        assertNotNull( result );
        System.out.print( result );
    }

}
