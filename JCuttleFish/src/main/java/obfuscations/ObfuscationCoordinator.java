package obfuscations;

import extractor.PathsExtractor;
import extractor.filefilters.SuffixFolderFilter;
import extractor.filefilters.enums.SuffixFilters;
import obfuscations.layout.LayoutManager;
import org.apache.commons.io.FileUtils;
import parser.UnitSourceInitiator;
import pojo.UnitSource;
import providers.FileSourceCodeProvider;
import util.BackupFilesHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;


//manages the different types of obfuscations and the order they will be applied.
public class ObfuscationCoordinator
{

    public ObfuscationCoordinator ( String originalAbsolutePath, String backupAbsolutePath )
    {
//        File originalLocation = new File( "C:/target/main/java" );
//        File backupLocation = new File( "C:/freshbackup" );

        File originalLocation = new File( originalAbsolutePath );
        File backupLocation = new File( backupAbsolutePath );
        this.backupFilesAtLocation( originalLocation, backupLocation );

        PathsExtractor pathsExtractor = new PathsExtractor( originalLocation.getAbsolutePath() );
        List<File> originalFiles = pathsExtractor.getFilesInstances( new SuffixFolderFilter( SuffixFilters.JAVA ) );

        UnitSourceInitiator initiator = new UnitSourceInitiator();

        obfuscations.layout.LayoutManager layoutManager = new LayoutManager();
        FileSourceCodeProvider fileSourceCodeProvider = new FileSourceCodeProvider();
        for ( File file : originalFiles )
        {
            UnitSource unitSource = initiator.fetchUnitSource( fileSourceCodeProvider.get( file ) );
            unitSource = layoutManager.obfuscate( unitSource );
            this.writeUnitSourceToFile( file, unitSource );
        }
    }

    private void backupFilesAtLocation ( File originalLocation, File backupLocation )
    {
        BackupFilesHelper.backupFiles( originalLocation, backupLocation );
    }

    private void writeUnitSourceToFile ( File file, UnitSource unitSource )
    {
        try
        {
            FileUtils.writeStringToFile( file, unitSource.getDocument().get() );
        } catch ( IOException ioe )
        {
            ioe.printStackTrace();
        }
    }
}