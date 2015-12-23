package pojo;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.text.Document;
import util.ConvenienceWrappers;

import java.io.File;


public class UnitSource
{

    private final CompilationUnit compilationUnit;
    private final File file;
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

    public Document getDocument ()
    {
        return this.document;
    }

    public TypeDeclaration getTypeDeclarationIfIsClass ()
    {
        if ( ConvenienceWrappers.isValidCompilationUnit( this.compilationUnit ) )
        {
            TypeDeclaration typeDeclaration = ( TypeDeclaration )this.compilationUnit.types().get( 0 );
            if ( typeDeclaration.resolveBinding().isClass() )
            {
                return typeDeclaration;
            }
        }

        throw new RuntimeException( "Type declaration is not a class" );
    }
}