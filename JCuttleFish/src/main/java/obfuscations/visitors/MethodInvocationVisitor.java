package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

import java.util.Collection;
import java.util.List;


public class MethodInvocationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public MethodInvocationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( MethodInvocation methodInvocation )
    {
        this.callbacks.stream().forEach( c -> c.notify( methodInvocation ) );

        if ( methodInvocation.getExpression() != null )
        {
            new ExpressionVisitor( this.callbacks ).visit( methodInvocation.getExpression() );
        }

        //Rename and thisify arguments
        List<Object> arguments = methodInvocation.arguments();
        //ModifyAst.renameMethodInvocationArguments( arguments, this.obfuscationInfo );
        new MethodArgumentsVisitor( this.callbacks ).visit( arguments );
        return false;
    }
}
