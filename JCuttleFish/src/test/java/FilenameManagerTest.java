import configuration.ConfigurationEnvironment;
import obfuscations.NodeFinder;
import obfuscations.filenameobfuscation.FilenameManager;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import parser.UnitSourceInitiator;
import pojo.UnitNode;
import providers.FileSourceCodeProvider;
import providers.ObfuscatedNamesProvider;
import util.enums.ObfuscatedNamesVariations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class FilenameManagerTest
{

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private UnitSourceInitiator initiator = new UnitSourceInitiator();
    private NodeFinder nodeFinder = new NodeFinder();
    private FileSourceCodeProvider sourceCodeProvider = new FileSourceCodeProvider();
    private ObfuscatedNamesProvider obfuscatedNamesProvider = new ObfuscatedNamesProvider();
    private File root;

    @Before
    public void setEmptyConfig () throws IOException
    {
        this.root = this.temporaryFolder.newFolder( "root" );
        ConfigurationEnvironment.createConfigurationInstance( this.root.getAbsolutePath() );
    }

    @Test
    public void classRefsTest () throws IOException
    {
        File pack1 = this.temporaryFolder.newFolder( "root/pack1" );

        File pack11 = this.temporaryFolder.newFolder( "root/pack1/pack11" );
        File we = this.temporaryFolder.newFile( "root/pack1/pack11/we.java" );

        File two = this.temporaryFolder.newFile( "root/pack1/Two.java" );
        File pack2 = this.temporaryFolder.newFolder( "root/pack2" );
        File main = this.temporaryFolder.newFile( "root/pack2/Main.java" );
        File one = this.temporaryFolder.newFile( "root/pack2/One.java" );

        FileUtils.writeStringToFile( two, this.sourceCodeProvider.get( new File( "src/test/resources/sampleapplications/classrefs/original/pack1/Two.java" ) ) );
        FileUtils.writeStringToFile( main, this.sourceCodeProvider.get( new File( "src/test/resources/sampleapplications/classrefs/original/pack2/Main.java" ) ) );
        FileUtils.writeStringToFile( one, this.sourceCodeProvider.get( new File( "src/test/resources/sampleapplications/classrefs/original/pack2/One.java" ) ) );

        List<File> originalFiles = new ArrayList<>();
        originalFiles.add( two );
        originalFiles.add( main );
        originalFiles.add( one );
        List<UnitNode> unitNodes = ( List<UnitNode> )this.nodeFinder
                .getUnitNodesCollectionFromUnitSources( this.initiator.fetchUnitSourceCollection( originalFiles ) );

        FilenameManager filenameManager = new FilenameManager( this.root );
        filenameManager.obfuscate( unitNodes );

        Deque<String> obfuscatedFileNames = this.obfuscatedNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );
        Deque<String> obfuscatedFolderNames = this.obfuscatedNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );
        //Assert file names
        assertEquals( obfuscatedFileNames.pollFirst() + ".java", unitNodes.get( 0 ).getUnitSource().getFile().getName() );
        assertEquals( "Main.java", unitNodes.get( 1 ).getUnitSource().getFile().getName() );
        assertEquals( obfuscatedFileNames.pollFirst() + ".java", unitNodes.get( 2 ).getUnitSource().getFile().getName() );
        //Assert folder names
        List<String> rootFolderContents = Arrays.asList( this.root.list() );
        assertEquals( obfuscatedFolderNames.pollFirst(), rootFolderContents.get( 0 ) );
        assertEquals( "pack2", rootFolderContents.get( 1 ) );

    }
}
