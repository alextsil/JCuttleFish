package pojo;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;


public class UnitSource
{

    private CompilationUnit compilationUnit;
    //Hosts the source code as text
    private Document document;

    public UnitSource ( CompilationUnit compilationUnit, String sourceCode )
    {
        this.compilationUnit = compilationUnit;
        this.document = new Document( sourceCode );
    }

    public CompilationUnit getCompilationUnit ()
    {
        return compilationUnit;
    }

    public Document getDocument ()
    {
        return document;
    }

}
