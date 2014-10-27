package obfuscations.accessmodification;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AccessModificator {
	
	private File targetFile;
	private String targetLine;

	
	public AccessModificator(File targetFile) {
		this.targetFile = targetFile;
	}
		
	public void modifyFileToPublic() {
		File newFile = createNewFileFromPath(this.targetFile);
		
		try(Scanner scanner = new Scanner(this.targetFile,("UTF-8"));
			PrintWriter writer = new PrintWriter(newFile,"UTF-8")) {
			
			String pattern = "protected|private";
			Pattern patImpl = Pattern.compile(pattern);
			//read-write file line by line
			scanner.useDelimiter(";");
			while (scanner.hasNext()) {
				targetLine = scanner.nextLine();	
				Matcher matcher = patImpl.matcher(targetLine);
				targetLine = matcher.replaceFirst("public");
				writer.println(targetLine);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		this.targetFile.delete();
		newFile.renameTo(targetFile);
	}
	
	//Creates a new File using the path and name of the file given as a parameter.
	//@returns - new File instance
	public File createNewFileFromPath(File targetFilePath) {
		File newFile = new File(targetFilePath.getAbsolutePath() + "new");		
		return newFile;
	}
}