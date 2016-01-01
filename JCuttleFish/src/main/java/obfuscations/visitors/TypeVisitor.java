package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Type;

import java.util.Collection;


public class TypeVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public TypeVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    public boolean visit ( Type type )
    {
        this.callbacks.stream().forEach( c -> c.notify( type ) );

        return false;
    }
}
