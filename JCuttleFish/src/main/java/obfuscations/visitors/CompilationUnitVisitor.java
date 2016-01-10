package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;


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

        List<AbstractTypeDeclaration> abstractTypeDeclarations = compilationUnit.types();
        abstractTypeDeclarations.stream()
                .forEach( atd -> new AbstractTypeDeclarationVisitor( this.callbacks ).visit( atd ) );

        compilationUnit.imports().stream()
                .forEach( impdecl -> new ImportDeclarationVisitor( this.callbacks ).visit( ( ImportDeclaration )impdecl ) );
        return false;
    }
}
