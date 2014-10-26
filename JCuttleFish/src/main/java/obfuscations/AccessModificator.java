package obfuscations;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//all public
public class AccessModificator {
	
	private File targetFile;
	private File newFile;
	private Scanner scanner;
	private String targetLine;
	private PrintWriter writer;
	
	
	public AccessModificator() {
		
	}
	
	
	public void targetFile(File file) {
		this.targetFile = file;
	}
	
	public void modifyToPublic() {
		//parsing
		try {
			this.scanner = new Scanner(this.targetFile,("UTF-8"));
			
			newFile = new File("C:\\Contact2.java\\");
			writer = new PrintWriter(newFile,"UTF-8");
			
			while (scanner.hasNext()) {
				targetLine = scanner.nextLine();
				//prepare regex - replace
				String pattern = "protected|private";
				Pattern patImpl = Pattern.compile(pattern);
				Matcher matcher = patImpl.matcher(targetLine);
				targetLine = matcher.replaceAll("public");
				//System.out.println(targetLine);
				writer.println(targetLine);
			} 
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			scanner.close();
			this.targetFile.delete();
			writer.close();
			newFile.renameTo(targetFile);
			
		}
	}
}