import java.awt.print.Printable;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.junit.Test;

public class reflectiontest {

	@Test
	public void test() {
		String userGivenPath = "src/test/resources/sampleapplications/addressbook/API/IGroup.java";
		File pathToFile = new File(userGivenPath);
		// System.out.print(pathToFile.get);

	}

	@Test
	public void iteratorTest() {
		String userGivenPath = "src/test/resources/sampleapplications/addressbook/API/IGroup.java";
		File file = new File(userGivenPath);
		LineIterator it = null;
		try {
			it = FileUtils.lineIterator(file, "UTF-8");
			while (it.hasNext()) {
				String line = it.nextLine();
				System.out.println(line);
			}
		}
		catch(IOException e){
			
		}
		finally {
			it.close();
		}
	}

}
