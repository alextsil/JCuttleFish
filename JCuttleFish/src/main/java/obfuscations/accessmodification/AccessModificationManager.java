package obfuscations.accessmodification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import extractor.PathsExtractor;
import extractor.filefilters.SupportedSuffixFilters;


//Wraps the 2 classes that provide the access modification service
public class AccessModificationManager {
	
	private String targetPath;
	
	
	public AccessModificationManager(String targetPath) {
		this.targetPath = targetPath;
	}
	
	public void modifyAllToPublic() {
		PathsExtractor extractor = new PathsExtractor(this.targetPath);
		List<File> paths = new ArrayList<File>();
		paths = extractor.getFilesInstances(SupportedSuffixFilters.JAVA);
		
		for (File file : paths) {
			AccessModificator aMod = new AccessModificator(file);
			aMod.modifyFileToPublic();
		}
	}
	

}
