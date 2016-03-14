package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;

import java.util.Collection;
import java.util.List;


public class SuperConstructorInvocationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public SuperConstructorInvocationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( SuperConstructorInvocation superConstructorInvocation )
    {
        this.callbacks.stream().forEach( c -> c.notify( superConstructorInvocation ) );

        List<Expression> arguments = superConstructorInvocation.arguments();
        arguments.stream().forEach( a -> new ExpressionVisitor( this.callbacks ).visit( a ) );
        return false;
    }
}
