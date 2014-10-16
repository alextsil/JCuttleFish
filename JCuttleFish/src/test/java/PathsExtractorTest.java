import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		userGivenPath = "C:\\intel\\";
		instance = new PathsExtractor(userGivenPath);
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
	}

	@Test
	public void test() {
		ArrayList<File> fash = new ArrayList<File>();
		fash = instance.getTargetFilesInstances();
		assertNotNull(fash);
		
		for (File file : fash) {
			System.out.println(file.toString());
		}
		
	}

}
