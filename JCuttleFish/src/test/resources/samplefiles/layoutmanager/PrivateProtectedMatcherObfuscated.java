package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class a {

    private String a = "protected|private";

    private String b;

    private Pattern c;

    private Matcher d;

    public a () {
        c = Pattern.compile( a );
    }

    public String applyMatcher ( String aa ) {
        this.b = aa;
        d = c.matcher( this.b );
        this.b = d.replaceFirst( "public" );
        return this.b;
    }
}
