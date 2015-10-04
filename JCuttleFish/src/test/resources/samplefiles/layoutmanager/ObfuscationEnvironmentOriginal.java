package test;

//Holds the target project specific paths and the JAVA_HOME path.
public class ObfuscationEnvironment {

    private String aaa;

    private String[] bbb = new String[ 1 ];

    private String[] ccc = new String[ 1 ];

    public ObfuscationEnvironment () {
        this.aaa = "JCuttleFish" + System.lineSeparator() + "src";
        this.bbb[ 0 ] = "C:/test/JCuttleFish/src";
        this.ccc[ 0 ] = System.getenv( "JAVA_HOME" ) + System.lineSeparator() + "bin";
    }

    public String getRelativeSourcePath () {
        return aaa;
    }

    public String[] getAbsoluteSourcePath () {
        return bbb;
    }

    public String[] getClasspath () {
        return ccc;
    }
}
