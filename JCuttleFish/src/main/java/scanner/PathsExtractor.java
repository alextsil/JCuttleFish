package scanner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//This class resolves the path that was given as an argument, verifies it's a non-empty path
//and saves the File instances of all FILES in a collection.

public class PathsExtractor {
	
	private String[] targetFilesPaths; //The list that contains all the file paths
	private List<File> targetFilesInstances; //The list that contains all the File instances from the extracted paths
	private String pathName; //User given path name
	
	
	public PathsExtractor(String pathName) {
		this.pathName = pathName;
		
	}
	
	//pernei to path kai bgazei se list ola ta paths twn arxeiwn pou vrike
	public void extractPaths() {
		File initialInstance = new File(this.pathName);
		targetFilesPaths = initialInstance.list();
		
		for (String singlePath : targetFilesPaths) {
			File singleInstance = new File(singlePath);
			
			if(initialInstance.isDirectory()) {
				extractPaths();
			}
			else if (initialInstance.isFile()) {
				this.targetFilesInstances.add(initialInstance);
			}
				
			
		}
		
	}
	
	//pernei ta paths kai ta metatrepei se File instances
	public void createFileInstancesFromPaths() {
		
	}


	public String getPathName() {
		return pathName;
	}
	
}
