package pojo;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;

import java.io.File;


public class UnitSource
{

    private final CompilationUnit compilationUnit;
    private File file;
    //Hosts the source code as text
    private final Document document;

    public UnitSource ( CompilationUnit compilationUnit, File file, String sourceCode )
    {
        this.compilationUnit = compilationUnit;
        this.file = file;
        this.document = new Document( sourceCode );
    }

    public CompilationUnit getCompilationUnit ()
    {
        return this.compilationUnit;
    }

    public File getFile ()
    {
        return this.file;
    }

    public void setFile ( File file )
    {
        this.file = file;
    }

    public Document getDocument ()
    {
        return this.document;
    }
}