package test;

import extractor.filefilters.enums.SuffixFilters;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.File;
import java.io.FileFilter;


public class SuffixFolderFilterOriginal implements FileFilter {

    private String a;

    public SuffixFolderFilterOriginal ( SuffixFilters aa ) {
        this.a = aa.toString();
    }

    // using commonsio filter implementations
    //The directory filter ensures that the filter will only replace on files. - do not remove.
    public boolean accept ( File aa ) {
        return new SuffixFileFilter( this.a ).accept( aa )
                || DirectoryFileFilter.INSTANCE.accept( aa );
    }

}
