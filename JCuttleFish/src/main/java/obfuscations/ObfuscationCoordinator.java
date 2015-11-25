package obfuscations;

import extractor.PathsExtractor;
import extractor.filefilters.SuffixFolderFilter;
import extractor.filefilters.enums.SuffixFilters;
import obfuscations.layout.LayoutManager;
import parser.UnitSourceInitiator;
import pojo.UnitSource;
import providers.FileSourceCodeProvider;
import util.BackupFilesHelper;

import java.io.File;
import java.util.List;


//manages the different types of obfuscations and the order they will be applied.
public class ObfuscationCoordinator
{

    public ObfuscationCoordinator ()
    {
        File originalLocation = new File( "C:/target/main/java" );
        File backupLocation = new File( "C:/Users/Alexei/Desktop" );
        this.backupFilesAtLocation( originalLocation, backupLocation );

        PathsExtractor pathsExtractor = new PathsExtractor( originalLocation.getAbsolutePath() );
        List<File> originalFiles = pathsExtractor.getFilesInstances( new SuffixFolderFilter( SuffixFilters.JAVA ) );

        obfuscations.layout.LayoutManager layoutManager = new LayoutManager();

        originalFiles.stream().forEach( f -> layoutManager.obfuscate( this.fetchUnitSourceFromTarget( f ) ) );
    }

    private UnitSource fetchUnitSourceFromTarget ( File originalFileLocation )
    {
        return new UnitSourceInitiator().fetchUnitSource( new FileSourceCodeProvider().get( originalFileLocation ) );
    }

    private void backupFilesAtLocation ( File originalLocation, File backupLocation )
    {
        BackupFilesHelper.backupFiles( originalLocation, backupLocation );
    }
}