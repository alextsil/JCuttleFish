import obfuscations.ObfuscationCoordinator;


public class Main
{

    public static void main ( String[] args )
    {
        String originalLocationPath = args[ 0 ];
        String backupLocationPath = args[ 1 ];

        new ObfuscationCoordinator( originalLocationPath, backupLocationPath );
    }

}
