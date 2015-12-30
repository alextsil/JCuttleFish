import configuration.ConfigurationEnvironment;
import obfuscations.ObfuscationCoordinator;


public class Main
{

    public static void main ( String[] args )
    {
        String originalLocationPath = args[ 0 ];
        String backupLocationPath = args[ 1 ];

        ConfigurationEnvironment.createConfigurationInstance( originalLocationPath );

        new ObfuscationCoordinator( originalLocationPath, backupLocationPath );
    }

}
