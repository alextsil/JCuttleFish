package test;

import extractor.filefilters.enums.SuffixFilters;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.io.FileFilter;


public class SuffixFolderFilterOriginal implements FileFilter {

    private String a;

    public SuffixFolderFilterOriginal ( SuffixFilters suffixFilter ) {
        this.a = suffixFilter.toString();
    }

    // using commonsio filter implementations
    //The directory filter ensures that the filter will only replace on files. - do not remove.
    public boolean accept ( File pathName ) {
        return new SuffixFileFilter( this.a ).accept( pathName )
                || DirectoryFileFilter.INSTANCE.accept( pathName );
    }

}
