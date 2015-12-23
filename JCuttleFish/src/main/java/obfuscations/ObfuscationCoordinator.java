package obfuscations;

import extractor.PathsExtractor;
import extractor.filefilters.SuffixFolderFilter;
import extractor.filefilters.enums.SuffixFilters;
import obfuscations.layout.LayoutManager;
import org.apache.commons.io.FileUtils;
import parser.UnitSourceInitiator;
import pojo.UnitSource;
import util.BackupFilesHelper;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
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

        Collection<File> originalFiles = this.getAbsolutePaths( originalLocation.getAbsolutePath() );

        UnitSourceInitiator initiator = new UnitSourceInitiator();

        Collection<UnitSource> unitSources = initiator.fetchUnitSourceCollection( originalFiles );
        //TODO : call nodeFinder and map the UnitSource list to a UnitNode list -> pass into layoutmanager
        obfuscations.layout.LayoutManager layoutManager = new LayoutManager();
        layoutManager.obfuscate( unitSources );

        this.saveUnitSourcesToFiles( unitSources );

    }

    private void backupFilesAtLocation ( File originalLocation, File backupLocation )
    {
        BackupFilesHelper.backupFiles( originalLocation, backupLocation );
    }

    private void saveUnitSourcesToFiles ( Collection<UnitSource> unitSources )
    {
        for ( UnitSource unitSource : unitSources )
        {
            try
            {
                FileUtils.writeStringToFile( unitSource.getFile(), unitSource.getDocument().get() );
            } catch ( IOException ioe )
            {
                ioe.printStackTrace();
            }
        }
    }

    private Collection<File> getAbsolutePaths ( String rootAbsolutePath )
    {
        PathsExtractor pathsExtractor = new PathsExtractor( rootAbsolutePath );
        List<File> originalFiles = pathsExtractor.getFilesInstances( new SuffixFolderFilter( SuffixFilters.JAVA ) );
        return originalFiles;
    }
}