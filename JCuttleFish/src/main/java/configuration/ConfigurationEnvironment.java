package configuration;

//Holds the target project specific paths and the JAVA_HOME path.
public final class ConfigurationEnvironment
{

    private static ConfigurationEnvironment instance = null;
    private String relativeSourcePath;
    private String[] absoluteSourcePath = new String[ 1 ];
    private String[] classpath = new String[ 1 ];

    private ConfigurationEnvironment ( String absoluteSourcePath )
    {
//      relative source path not useful for now.
        this.relativeSourcePath = "";//"JCuttleFish" + System.lineSeparator() + "src";
        this.absoluteSourcePath[ 0 ] = absoluteSourcePath;
        this.classpath[ 0 ] = System.getenv( "JAVA_HOME" ) + "\\jre\\lib\\rt.jar";
    }

    public static ConfigurationEnvironment getInstance ()
    {
        if ( instance == null )
        {
            throw new RuntimeException( "Source path not set. Please use createConfigurationInstance first." );
        }
        return instance;
    }

    public static void createConfigurationInstance ( String absoluteSourcePath )
    {
        instance = new ConfigurationEnvironment( absoluteSourcePath );
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
}
