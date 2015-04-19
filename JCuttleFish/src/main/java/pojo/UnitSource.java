package pojo;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class UnitSource {

    private CompilationUnit compilationUnit;

    private String sourceCode;

    public UnitSource ( CompilationUnit compilationUnit, String sourceCode ) {
        this.compilationUnit = compilationUnit;
        this.sourceCode = sourceCode;
    }

    public CompilationUnit getCompilationUnit () {
        return compilationUnit;
    }

    public String getSourceCode () {
        return sourceCode;
    }
}
