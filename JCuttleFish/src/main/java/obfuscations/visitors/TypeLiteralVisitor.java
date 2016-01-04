package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeLiteral;

import java.util.Collection;


public class TypeLiteralVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public TypeLiteralVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( TypeLiteral typeLiteral )
    {
        this.callbacks.stream().forEach( c -> c.notify( typeLiteral ) );

        new TypeVisitor( this.callbacks ).visit( typeLiteral.getType() );
        return false;
    }
}
