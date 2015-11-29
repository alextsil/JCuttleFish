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
        File originalLocation = new File( "C:/1backup/JCuttleFish/src" );
        File backupLocation = new File( "C:/Users/Alexei/Desktop/backupcuttle" );
        this.backupFilesAtLocation( originalLocation, backupLocation );

        PathsExtractor pathsExtractor = new PathsExtractor( originalLocation.getAbsolutePath() );
        List<File> originalFiles = pathsExtractor.getFilesInstances( new SuffixFolderFilter( SuffixFilters.JAVA ) );

        UnitSourceInitiator initiator = new UnitSourceInitiator();

        obfuscations.layout.LayoutManager layoutManager = new LayoutManager();
        FileSourceCodeProvider fileSourceCodeProvider = new FileSourceCodeProvider();
        for ( File file : originalFiles )
        {
            UnitSource unitSource = initiator.fetchUnitSource( fileSourceCodeProvider.get( file ) );
            layoutManager.obfuscate( unitSource );
        }
    }

    private void backupFilesAtLocation ( File originalLocation, File backupLocation )
    {
        BackupFilesHelper.backupFiles( originalLocation, backupLocation );
    }
}