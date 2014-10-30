import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import extractor.filefilters.SuffixFilter;
import extractor.filefilters.SupportedSuffixFilters;


public class SuffixFilterTest {
	
	FileFilter concreteFilter;
	private File[] fileInstances;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		concreteFilter = null;
	}

	@Test
	public void iniFilterTest() {
		FileFilter concreteFilter = new SuffixFilter(SupportedSuffixFilters.INI);
		File initialFilePath = new File("src/test/resources/sampleapplications/addressbook/ini");
		fileInstances = initialFilePath.listFiles(concreteFilter);
		assertEquals(2, fileInstances.length);
	}

}
