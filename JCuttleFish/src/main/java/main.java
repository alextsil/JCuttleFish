import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {

		File testFile = new File("c:\\testFile.java");

		try {
			Scanner sc = new Scanner(testFile);
			while (sc.hasNextLine()) {
				String singleLine = sc.nextLine();
				System.out.println(singleLine);
			}
			sc.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

	}

}
