package obfuscations;

import configuration.ObfuscationEnvironment;
import obfuscations.layout.LayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


//manages the different types of obfuscations and the order they will be applied.
public class ObfuscationCoordinator
{

    public ObfuscationCoordinator ()
    {
        ObfuscationEnvironment obfuscationEnvironment = new ObfuscationEnvironment();

        List<File> targetJavaFiles = new ArrayList<>();
        //backup all
//        File userGivenBackupDir = new File( "C:/1backup" );
//        BackupFilesHelper.backupFiles( new File( obfuscationEnvironment.getAbsoluteSourcePath()[ 0 ] ), userGivenBackupDir );
//
//        //backup dir passed in pathsExtractor so the obfuscator won't modify the original files but the backup files.
//        //TODO : invert functionality b4 release.
//        PathsExtractor pathsExtractor = new PathsExtractor( userGivenBackupDir + "/main/java" );
//
//        List<File> targetJavaFiles = pathsExtractor.getFilesInstances( new SuffixFolderFilter( SuffixFilters.JAVA ) );
//
//        //AccessModificationManager accessModificator = new AccessModificationManager();
//        //accessModificator.obfuscate( targetJavaFiles );

        //send pathsExtractor in only. (for testing)
        LayoutManager layoutManager = new LayoutManager();
//        targetJavaFiles.clear();
        //targetJavaFiles.add( new File( "C:/1backup/main/java/extractor/PathsExtractor.java" ) );
        targetJavaFiles.add( new File( "C:/1backup/main/java/pojo/UnitSource.java" ) );
        layoutManager.obfuscate( targetJavaFiles );

    }
}
