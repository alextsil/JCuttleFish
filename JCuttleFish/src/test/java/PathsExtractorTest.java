import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import extractor.PathsExtractor;
import extractor.filefilters.SuffixFolderFilter;
import extractor.filefilters.SupportedSuffixFilters;

public class PathsExtractorTest {

	private String userGivenPath;
	private PathsExtractor instance;
	private File pathToFile;
	private List<File> resultsList;
	private FileFilter filterImpl;

	@Before
	public void setUp() throws Exception {
		userGivenPath = "src/test/resources/sampleapplications/addressbook";
		pathToFile = new File(userGivenPath);
		instance = new PathsExtractor(pathToFile.getAbsolutePath());
		resultsList = new ArrayList<File>();
	}

	@After
	public void tearDown() throws Exception {
		instance = null;
		pathToFile = null;
		resultsList = null;
		filterImpl = null;
	}

	@Test
	public void getFilesInstancesUnfilteredTest() {
		resultsList = instance.getFilesInstances();
		assertNotNull(resultsList);
		assertEquals(35, resultsList.size());

		// for (File file : resultsList) {
		// System.out.println(file.toString());
		// }
	}

	@Test
	public void getFilesInstancesFilteredTest() {
		filterImpl = new SuffixFolderFilter(SupportedSuffixFilters.JAVA);
		resultsList = instance.getFilesInstances(filterImpl);
		assertNotNull(resultsList);
		assertEquals(28, resultsList.size());

		filterImpl = new SuffixFolderFilter(SupportedSuffixFilters.FORM);
		resultsList = instance.getFilesInstances(filterImpl);
		assertNotNull(resultsList);
		assertEquals(3, resultsList.size());
	}

}
