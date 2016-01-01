package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import java.util.Collection;


public class FieldDeclarationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public FieldDeclarationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( FieldDeclaration fieldDeclaration )
    {
        this.callbacks.stream().forEach( c -> c.notify( fieldDeclaration ) );

        new TypeVisitor( this.callbacks ).visit( fieldDeclaration.getType() );
        return false;
    }
}
