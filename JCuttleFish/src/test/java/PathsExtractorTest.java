import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import scanner.PathsExtractor;
import scanner.filefilters.SuffixFilter;
import scanner.filefilters.SupportedSuffixFilters;


public class PathsExtractorTest {

	private String userGivenPath;
	private PathsExtractor instance;
	private File relativeToAbsolute;
	private List<File> resultsList;
	
	
	@Before
	public void setUp() throws Exception {
		userGivenPath = "src/test/resources/sampleapplications/addressbook";
		relativeToAbsolute = new File(userGivenPath);
		instance = new PathsExtractor(relativeToAbsolute.getAbsolutePath());
		
		resultsList = new ArrayList<File>();
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
		relativeToAbsolute = null;
		resultsList = null;
	}

	@Test
	public void unfilteredTest() {
		resultsList = instance.getFilesInstances();
		assertNotNull(resultsList);
		
		for (File file : resultsList) {
			System.out.println(file.toString());
		}
		System.out.println(resultsList.size());	
	}
	
	@Test
	public void filteredTest() {
		//TODO: kripse to filefilter stin pathsextractor
		FileFilter filter = new SuffixFilter(SupportedSuffixFilters.JAVA);
		resultsList = instance.getFilesInstances(filter);
		assertNotNull(resultsList);
		
		for (File file : resultsList) {
			System.out.println(file.toString());
		}
		System.out.println(resultsList.size());	
	}

}
