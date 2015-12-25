package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PostfixExpression;

import java.util.Collection;


public class PostfixExpressionVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public PostfixExpressionVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( PostfixExpression postfixExpression )
    {
        this.callbacks.stream().forEach( c -> c.notify( postfixExpression ) );

        new ExpressionVisitor( this.callbacks ).visit( postfixExpression.getOperand() );
        return false;
    }
}
