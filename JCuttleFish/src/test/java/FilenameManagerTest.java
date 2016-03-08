import obfuscations.NodeFinder;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import parser.UnitSourceInitiator;
import providers.FileSourceCodeProvider;

import java.io.File;
import java.io.IOException;


public class FilenameManagerTest
{

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private UnitSourceInitiator initiator = new UnitSourceInitiator();
    private NodeFinder nodeFinder = new NodeFinder();
    private FileSourceCodeProvider sourceCodeProvider = new FileSourceCodeProvider();

    @Test
    public void classRefsTest () throws IOException
    {
        File root = this.temporaryFolder.newFolder( "root" );
        File pack1 = this.temporaryFolder.newFolder( "root/pack1" );
        File two = this.temporaryFolder.newFile( "root/pack1/Two.java" );
        File pack2 = this.temporaryFolder.newFolder( "root/pack2" );
        File main = this.temporaryFolder.newFile( "root/pack2/Main.java" );
        File one = this.temporaryFolder.newFile( "root/pack2/One.java" );

        FileUtils.writeStringToFile( two, this.sourceCodeProvider.get( new File( "src/test/resources/sampleapplications/classrefs/original/pack1/Two.java" ) ) );
        FileUtils.writeStringToFile( main, this.sourceCodeProvider.get( new File( "src/test/resources/sampleapplications/classrefs/original/pack2/Main.java" ) ) );
        FileUtils.writeStringToFile( one, this.sourceCodeProvider.get( new File( "src/test/resources/sampleapplications/classrefs/original/pack2/One.java" ) ) );
    }
}
