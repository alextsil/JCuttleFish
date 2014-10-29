package obfuscations.accessmodification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import obfuscations.IObfuscation;
import extractor.PathsExtractor;
import extractor.filefilters.SupportedSuffixFilters;


//Wraps the 2 classes that provide the access modification service
public class AccessModificationManager implements IObfuscation {
	
	private String targetPath;
	
	
	public AccessModificationManager() {
	}
	
	public boolean obfuscate() {
		PathsExtractor extractor = new PathsExtractor(this.targetPath);
		List<File> paths = new ArrayList<File>();
		paths = extractor.getFilesInstances(SupportedSuffixFilters.JAVA);
		
		for (File file : paths) {
			AccessModificator aMod = new AccessModificator(file);
			aMod.modifyFileToPublic();
		}
		return true; //success or failure
	}
	
	public void setPath(String path) {
		this.targetPath = path;
	}
	

}
