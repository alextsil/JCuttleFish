package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
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
    public boolean visit ( PostfixExpression node )
    {
        new ExpressionVisitor( this.callbacks ).visit( node.getOperand() );
        return false;
    }
}
