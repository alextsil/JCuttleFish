package test;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class UnitSource {

    private CompilationUnit a;

    private String b;

    public UnitSource ( CompilationUnit a, String b ) {
        this.a = a;
        this.b = b;
    }

    public CompilationUnit getCompilationUnit () {
        return a;
    }

    public String getSourceCode () {
        return b;
    }
}
