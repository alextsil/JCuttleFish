package obfuscations;

import configuration.ObfuscationEnvironment;
import extractor.PathsExtractor;
import extractor.filefilters.SuffixFolderFilter;
import extractor.filefilters.enums.SupportedSuffixFilters;
import obfuscations.accessmodification.AccessModificationManager;

import java.io.File;
import java.util.List;

//manages the different types of obfuscations.
public class ObfuscationCoordinator {

    public ObfuscationCoordinator () {
        ObfuscationEnvironment obfuscationEnvironment = new ObfuscationEnvironment();

        PathsExtractor pathsExtractor = new PathsExtractor( obfuscationEnvironment.getAbsoluteSourcePath()[ 0 ] + "\\main\\java" );
        List<File> targetFiles = pathsExtractor.getFilesInstances( new SuffixFolderFilter( SupportedSuffixFilters.JAVA ) );

        AccessModificationManager accessModificator = new AccessModificationManager();
        accessModificator.obfuscate( targetFiles );

    }
}
