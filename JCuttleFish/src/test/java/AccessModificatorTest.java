import static org.junit.Assert.*;

import java.io.File;

import obfuscations.accessmodification.AccessModificator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class AccessModificatorTest {
	
	private File targetFile;
	private AccessModificator modificator;

	@Before
	public void setUp() throws Exception {
		targetFile = new File("C:\\test\\test.java\\");
		this.modificator = new AccessModificator(targetFile);
	}

	@After
	public void tearDown() throws Exception {
		this.modificator = null;
	}

	
	@Test
	public void testFash() {
//		modificator.modifyFileToPublic();	
//		assertNotNull(this.modificator);
		fail("not implemented");
	}

}
