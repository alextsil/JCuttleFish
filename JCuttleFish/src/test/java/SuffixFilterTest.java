import extractor.filefilters.SuffixFolderFilter;
import extractor.filefilters.enums.SuffixFilters;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;

import static org.junit.Assert.assertEquals;

public class SuffixFilterTest {

    FileFilter concreteFilter;

    private File[] fileInstances;

    private final Logger logger = LoggerFactory.getLogger( SuffixFilterTest.class );

    @Before
    public void setUp () throws Exception {
    }

    @After
    public void tearDown () throws Exception {
        concreteFilter = null;
    }

    @Test
    public void iniFilterTest () {
        FileFilter concreteFilter = new SuffixFolderFilter( SuffixFilters.INI );
        File initialFilePath = new File( "src/test/resources/sampleapplications/addressbook/ini" );
        fileInstances = initialFilePath.listFiles( concreteFilter );
        assertEquals( 2, fileInstances.length );
    }

}
