package test;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class a {

    private CompilationUnit a;

    private String b;

    public a ( CompilationUnit aa, String bb ) {
        this.a = aa;
        this.b = bb;
    }

    public CompilationUnit a () {
        return a;
    }

    public String b () {
        return b;
    }
}
