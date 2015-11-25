package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PostfixExpression;
import pojo.ObfuscationInfo;


public class PostfixExpressionVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public PostfixExpressionVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( PostfixExpression node )
    {
        new ExpressionVisitor( this.obfuscationInfo ).visit( node.getOperand() );
        return false;
    }
}
