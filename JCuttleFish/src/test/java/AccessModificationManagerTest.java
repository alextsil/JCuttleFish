import static org.junit.Assert.*;

import java.io.File;

import obfuscations.accessmodification.AccessModificationManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class AccessModificationManagerTest {
	
	private AccessModificationManager manager;

	@Before
	public void setUp() throws Exception {
		this.manager = new AccessModificationManager("C:\\test\\");
	}

	@After
	public void tearDown() throws Exception {
		this.manager = null;
	}

	@Test
	public void test() {
		this.manager.modifyAllToPublic();
		fail("not implemented");
	}

}
