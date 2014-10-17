package scanner.filefilters;

import java.io.File;
import java.io.FileFilter;

//Filter files based on file suffixes
public class SuffixFilter implements FileFilter {

	private String fileSuffix;
	
	public SuffixFilter(SupportedSuffixFilters suffixFilter) {
		this.fileSuffix = suffixFilter.toString();
	}
	
	//pathName.isDirectory() ensures that the filter will only apply on files. - do not remove.
	public boolean accept(File pathName) {
		if(pathName.toString().endsWith(this.fileSuffix) || pathName.isDirectory() ) {
			return true;
		}
		else {
			return false;
		}
	}
}
