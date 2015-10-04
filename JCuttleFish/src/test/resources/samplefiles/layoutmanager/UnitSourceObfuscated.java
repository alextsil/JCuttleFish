package test;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class UnitSource {

    private CompilationUnit a;

    private String b;

    public UnitSource ( CompilationUnit compilationUnit, String sourceCode ) {
        this.a = compilationUnit;
        this.b = sourceCode;
    }

    public CompilationUnit getCompilationUnit () {
        return this.a;
    }

    public String getSourceCode () {
        return this.b;
    }
}
