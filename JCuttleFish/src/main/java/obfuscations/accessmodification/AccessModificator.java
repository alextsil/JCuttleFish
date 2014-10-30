package obfuscations.accessmodification;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import regex.PrivateProtectedMatcher;


public class AccessModificator {
	
	private File targetFile;
	private String targetLine;

	
	public AccessModificator(File targetFile) {
		this.targetFile = targetFile;
	}
	
	//Creates a new File in the same dir as targetFile, copies the target file line by line
	//and replaces the first occurrence of the string:(private or protected) of each line with 
	//the string public. The target file gets deleted and the new file gets renamed to the target file.
	//@Returns - boolean, success or failure.
	public boolean modifyFileToPublic() {
		File newFile = createNewFileFromPath(this.targetFile);
		PrivateProtectedMatcher matcher = new PrivateProtectedMatcher();
		
		try(Scanner scanner = new Scanner(this.targetFile,("UTF-8"));
			PrintWriter writer = new PrintWriter(newFile,"UTF-8")) {

			scanner.useDelimiter(";");
			while (scanner.hasNext()) {
				targetLine = matcher.applyMatcher(scanner.nextLine()); 	
				writer.println(targetLine);
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		
		this.targetFile.delete();
		return newFile.renameTo(targetFile); //TODO: success or failure
	}
	
	//Creates a new File using the path and name of the file given as a parameter.
	//@returns - new File instance
	public File createNewFileFromPath(File targetFilePath) {
		File newFile = new File(targetFilePath.getAbsolutePath() + "new");		
		return newFile;
	}
}