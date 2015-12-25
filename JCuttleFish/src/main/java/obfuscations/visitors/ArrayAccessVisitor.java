package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public class ArrayAccessVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;
    private final Logger logger = LoggerFactory.getLogger( ArrayAccessVisitor.class );

    public ArrayAccessVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( ArrayAccess arrayAccess )
    {
        this.callbacks.stream().forEach( c -> c.notify( arrayAccess ) );

        new ExpressionVisitor( this.callbacks ).visit( arrayAccess.getArray() );
        new ExpressionVisitor( this.callbacks ).visit( arrayAccess.getIndex() );

        return false;
    }
}
