package configuration;

//Holds the target project specific paths and the JAVA_HOME path.
public class ObfuscationEnvironment
{

    private String relativeSourcePath;
    private String[] absoluteSourcePath = new String[ 1 ];
    private String[] classpath = new String[ 1 ];

    public ObfuscationEnvironment ()
    {
        this.relativeSourcePath = "JCuttleFish" + System.lineSeparator() + "src";
        this.absoluteSourcePath[ 0 ] = "C:/test/JCuttleFish/src";
        this.classpath[ 0 ] = System.getenv( "JAVA_HOME" ) + System.lineSeparator() + "bin";
    }

    public String getRelativeSourcePath ()
    {
        return this.relativeSourcePath;
    }

    public String[] getAbsoluteSourcePath ()
    {
        return this.absoluteSourcePath;
    }

    public String[] getClasspath ()
    {
        return this.classpath;
    }

    public void setRelativeSourcePath ( String relativeSourcePath )
    {
        this.relativeSourcePath = relativeSourcePath;
    }

    public void setAbsoluteSourcePath ( String[] absoluteSourcePath )
    {
        this.absoluteSourcePath = absoluteSourcePath;
    }

    public void setClasspath ( String[] classpath )
    {
        this.classpath = classpath;
    }
}
