import static org.junit.Assert.*;

import java.io.File;

import obfuscations.AccessModificator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;


public class AccessModificatorTest {
	private File singleTargetFile;
	private AccessModificator allPublicizer;

	@Before
	public void setUp() throws Exception {
		singleTargetFile = new File("C:\\Contact.java\\");
		allPublicizer = new AccessModificator();
		allPublicizer.targetFile(singleTargetFile);
	}

	@After
	public void tearDown() throws Exception {
		singleTargetFile = null;
		allPublicizer = null;
	}

	@Test
	public void test() {
		allPublicizer.modifyToPublic();
		assertNotNull(allPublicizer);
	}

}
