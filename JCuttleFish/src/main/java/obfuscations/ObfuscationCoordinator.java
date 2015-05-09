package obfuscations;

import configuration.ObfuscationEnvironment;
import extractor.PathsExtractor;
import extractor.filefilters.SuffixFolderFilter;
import extractor.filefilters.enums.SupportedSuffixFilters;
import obfuscations.accessmodification.AccessModificationManager;
import obfuscations.layout.LayoutManager;
import util.BackupFilesHelper;

import java.io.File;
import java.util.List;

//manages the different types of obfuscations and the order they will be applied.
public class ObfuscationCoordinator {

    public ObfuscationCoordinator () {
        ObfuscationEnvironment obfuscationEnvironment = new ObfuscationEnvironment();

        //backup all
        File userGivenBackupDir = new File( "C:/1backup" );
        BackupFilesHelper.backupFiles( new File( obfuscationEnvironment.getAbsoluteSourcePath()[ 0 ] ), userGivenBackupDir );

        //backup dir passed in pathsExtractor so the obfuscator won't modify the original files but the backup files.
        //TODO : invert functionality b4 release.
        PathsExtractor pathsExtractor = new PathsExtractor( userGivenBackupDir + "/main/java" );

        List<File> targetFilesJava = pathsExtractor.getFilesInstances( new SuffixFolderFilter( SupportedSuffixFilters.JAVA ) );

        //AccessModificationManager accessModificator = new AccessModificationManager();
        //accessModificator.obfuscate( targetFilesJava );

        //send pathsExtractor in only. (for testing)
        LayoutManager layoutManager = new LayoutManager();
        targetFilesJava.clear();
        targetFilesJava.add( new File( "C:/test/JCuttleFish/src/main/java/extractor/PathsExtractor.java" ) );
        layoutManager.obfuscate( targetFilesJava );

    }
}
