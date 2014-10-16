import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import scanner.PathsExtractor;


public class PathsExtractorTest {

	private String userGivenPath;
	private PathsExtractor instance;
	private File fileIn;
	
	
	@Before
	public void setUp() throws Exception {
		userGivenPath = "C:\\tet\\";
		instance = new PathsExtractor(userGivenPath);
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
	}

	@Test
	public void test() {	
		fileIn = instance.getTargetFile();
		System.out.println(instance.getPathName());
		
		assertTrue(fileIn.isDirectory());
		assertNotNull(fileIn);
	}

}
