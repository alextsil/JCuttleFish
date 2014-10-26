import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import extractor.PathsExtractor;
import extractor.filefilters.SupportedSuffixFilters;


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

//	@Test
//	public void unfilteredTest() {
//		resultsList = instance.getFilesInstances();
//		assertNotNull(resultsList);
//		
//		for (File file : resultsList) {
//			System.out.println(file.toString());
//		}
//		System.out.println(resultsList.size());	
//	}
	
	@Test
	public void filteredTest() {
		resultsList = instance.getFilesInstances(SupportedSuffixFilters.JAVA);
		assertNotNull(resultsList);
	
		for (File file : resultsList) {
			System.out.println(file.toString());
		}
		System.out.println(resultsList.size());	
		
		
		resultsList = instance.getFilesInstances(SupportedSuffixFilters.FORM);
		assertNotNull(resultsList);
		
		for (File file : resultsList) {
			System.out.println(file.toString());
		}
		System.out.println(resultsList.size());		
	}

}
