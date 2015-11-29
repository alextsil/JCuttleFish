package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.Expression;

import java.util.Collection;
import java.util.List;


public class ConstructorInvocationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public ConstructorInvocationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( ConstructorInvocation constructorInvocation )
    {
        this.callbacks.stream().forEach( c -> c.notify( constructorInvocation ) );

        List<Expression> arguments = constructorInvocation.arguments();
        arguments.stream().forEach( a -> new ExpressionVisitor( this.callbacks ).visit( a ) );
        return false;
    }
}
