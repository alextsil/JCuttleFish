import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import obfuscations.accessmodification.AccessModificator;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;


public class AccessModificatorTest {
	
	private File originalFile;
	private AccessModificator modificator;
	@Rule
	public TemporaryFolder tempFolderObj = new TemporaryFolder();
	private File createdFile;
	private File expectedFile;
	
	@Before
	public void setUp() throws Exception {
		//copies the original file into a temp file so the modificator won't edit the original file
		originalFile = new File("src/test/resources/sampleapplications/addressbook/model/Contact.java");
		createdFile = tempFolderObj.newFile("tempCopy.java");
		FileUtils.copyFile(originalFile, createdFile);
		
		this.modificator = new AccessModificator(createdFile);
		
		expectedFile = new File("src/test/resources/samplefiles/PrivateProtectedToPublicContact.java");
	}

	@After
	public void tearDown() throws Exception {
		this.modificator = null;
		this.originalFile = null;
		this.expectedFile = null;
		this.createdFile = null;
	}
	
	//compares the output file of the modifiation to a pre edited file. 
	@Test
	public void fileAsExpectedTest() {
		modificator.modifyFileToPublic();
		assertNotNull(this.modificator);
		try {
			assertTrue(FileUtils.contentEquals(createdFile, expectedFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
