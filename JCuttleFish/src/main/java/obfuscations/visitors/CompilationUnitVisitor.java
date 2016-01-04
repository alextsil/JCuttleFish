package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public class CompilationUnitVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;
    private final Logger logger = LoggerFactory.getLogger( CompilationUnitVisitor.class );

    public CompilationUnitVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( CompilationUnit compilationUnit )
    {
        this.callbacks.stream().forEach( c -> c.notify( compilationUnit ) );

        //TODO : visit all type declarations and not just the first one
        //FIXME : .types does not always return TypeDeclaration. Fix when you add tests for ENUMs
        new TypeDeclarationVisitor( this.callbacks ).visit( ( TypeDeclaration )compilationUnit.types().get( 0 ) );

        compilationUnit.imports().stream()
                .forEach( impdecl -> new ImportDeclarationVisitor( this.callbacks ).visit( ( ImportDeclaration )impdecl ) );
        return false;
    }
}
