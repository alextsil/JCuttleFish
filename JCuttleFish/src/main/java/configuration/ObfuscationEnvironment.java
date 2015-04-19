package configuration;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

//Holds the target project specific paths and the JAVA_HOME path.
public class ObfuscationEnvironment {

    private String relativeSourcePath;

    private String[] absoluteSourcePath = new String[ 1 ];

    private String[] classpath = new String[ 1 ];

    public ObfuscationEnvironment () {
        this.relativeSourcePath = "JCuttleFish" + System.lineSeparator() + "src";
        this.absoluteSourcePath[ 0 ] = "C:\\test\\JCuttleFish\\src";
        this.classpath[ 0 ] = System.getenv( "JAVA_HOME" ) + System.lineSeparator() + "bin";
        this.backupTarget();
    }

    public String getRelativeSourcePath () {
        return relativeSourcePath;
    }

    public String[] getAbsoluteSourcePath () {
        return absoluteSourcePath;
    }

    public String[] getClasspath () {
        return classpath;
    }

    private boolean backupTarget () {
        try {
            //does not overwrite if exists.
            FileUtils.copyDirectory( new File( absoluteSourcePath[ 0 ] ),
                    new File( absoluteSourcePath[ 0 ] + "\\backup" ) );
            return true;
        } catch ( IOException e ) {
            e.printStackTrace();
            return false;
        }
    }
}
