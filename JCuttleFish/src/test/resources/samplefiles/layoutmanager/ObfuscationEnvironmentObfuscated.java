package test;

//Holds the target project specific paths and the JAVA_HOME path.
public class a {

    private String a;

    private String[] b = new String[ 1 ];

    private String[] c = new String[ 1 ];

    public a () {
        this.a = "JCuttleFish" + System.lineSeparator() + "src";
        this.b[ 0 ] = "C:/test/JCuttleFish/src";
        this.c[ 0 ] = System.getenv( "JAVA_HOME" ) + System.lineSeparator() + "bin";
    }

    public String getRelativeSourcePath () {
        return a;
    }

    public String[] getAbsoluteSourcePath () {
        return b;
    }

    public String[] getClasspath () {
        return c;
    }
}
