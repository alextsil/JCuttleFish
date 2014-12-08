package obfuscations.accessmodification;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import obfuscations.IObfuscation;
import extractor.PathsExtractor;
import extractor.filefilters.SuffixFolderFilter;
import extractor.filefilters.enums.SupportedSuffixFilters;

//Wraps the 2 classes that provide the access modification service
public class AccessModificationManager implements IObfuscation {

	private String targetPath;

	public AccessModificationManager() {
	}

	// TODO : na pernei concrete filtro (apo tin set parameters (vres tropo na
	// kanei combine ta filtra
	public boolean obfuscate() {
		PathsExtractor extractor = new PathsExtractor(this.targetPath);
		List<File> paths = new ArrayList<File>();
		FileFilter filterImpl = new SuffixFolderFilter(
				SupportedSuffixFilters.JAVA);
		paths = extractor.getFilesInstances(filterImpl);

		for (File file : paths) {
			AccessModificator aMod = new AccessModificator(file);
			aMod.modifyFileToPublic();
		}
		return true; // TODO: success or failure
	}

	public void setParameters(String path) {
		this.targetPath = path;
	}

}
