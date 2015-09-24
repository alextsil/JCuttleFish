package test;

import extractor.filefilters.enums.SuffixFilters;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.io.FileFilter;


public class SuffixFolderFilterOriginal implements FileFilter {

    private String fileSuffix;

    public SuffixFolderFilterOriginal ( SuffixFilters suffixFilter ) {
        this.fileSuffix = suffixFilter.toString();
    }

    // using commonsio filter implementations
    //The directory filter ensures that the filter will only apply on files. - do not remove.
    public boolean accept ( File pathName ) {
        return new SuffixFileFilter( this.fileSuffix ).accept( pathName )
                || DirectoryFileFilter.INSTANCE.accept( pathName );
    }

}
