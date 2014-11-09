package extractor.filefilters;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

public class SuffixFolderFilter implements FileFilter {

	private String fileSuffix;

	public SuffixFolderFilter(SupportedSuffixFilters suffixFilter) {
		this.fileSuffix = suffixFilter.toString();
	}

	// using commonsio filter implementations
	//The directory filter ensures that the filter will only apply on files. - do not remove.
	public boolean accept(File pathName) {
		if (new SuffixFileFilter(fileSuffix).accept(pathName)
				|| DirectoryFileFilter.INSTANCE.accept(pathName)) {
			return true;
		} else {
			return false;
		}
	}

}
