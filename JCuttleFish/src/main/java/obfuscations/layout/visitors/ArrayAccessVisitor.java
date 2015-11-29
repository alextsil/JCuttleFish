package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;

import java.util.Collection;


public class ArrayAccessVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public ArrayAccessVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( ArrayAccess arrayAccess )
    {
        this.callbacks.stream().forEach( c -> c.notify( arrayAccess ) );

        new ExpressionVisitor( this.callbacks ).visit( arrayAccess.getArray() );
        return false;
    }
}
