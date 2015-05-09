package configuration;

import com.sun.media.jfxmedia.logging.Logger;
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
        this.absoluteSourcePath[ 0 ] = "C:/test/JCuttleFish/src";
        this.classpath[ 0 ] = System.getenv( "JAVA_HOME" ) + System.lineSeparator() + "bin";
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
}
