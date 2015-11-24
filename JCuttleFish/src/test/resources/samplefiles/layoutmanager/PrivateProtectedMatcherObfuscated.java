package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrivateProtectedMatcher {

    private String a = "protected|private";

    private String b;

    private Pattern c;

    private Matcher d;

    public PrivateProtectedMatcher () {
        this.c = Pattern.compile( this.a );
    }

    public String applyMatcher ( String targetLine ) {
        this.b = targetLine;
        this.d = this.c.matcher( this.b );
        this.b = this.d.replaceFirst( "public" );
        return this.b;
    }
}
