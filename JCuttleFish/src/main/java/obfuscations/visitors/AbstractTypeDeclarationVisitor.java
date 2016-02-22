package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.*;

import java.util.Collection;


public class AbstractTypeDeclarationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public AbstractTypeDeclarationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    public boolean visit ( AbstractTypeDeclaration abstractTypeDeclaration )
    {
        this.callbacks.stream().forEach( c -> c.notify( abstractTypeDeclaration ) );

        if ( abstractTypeDeclaration instanceof TypeDeclaration )
        {
            new TypeDeclarationVisitor( this.callbacks ).visit( ( TypeDeclaration )abstractTypeDeclaration );
        } else if ( abstractTypeDeclaration instanceof EnumDeclaration )
        {
            //Do nothing
        } else if ( abstractTypeDeclaration instanceof AnnotationTypeDeclaration )
        {
            throw new RuntimeException( "not implemented" );
        }

        return false;
    }
}
