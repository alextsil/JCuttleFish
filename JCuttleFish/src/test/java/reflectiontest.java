import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;


public class reflectiontest {

	@Test
	public void test() {
		String userGivenPath = "src/test/resources/sampleapplications/addressbook/API/IGroup.java";
		File pathToFile = new File(userGivenPath);
		assertNotNull(pathToFile);
		System.out.print(pathToFile.getClass().toString());
		// String wtf = IMatcher.class.toString();
		// System.out.println(wtf);
	}

}
