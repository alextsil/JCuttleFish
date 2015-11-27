package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;

import java.util.Collection;
import java.util.List;


public class InfixExpressionVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public InfixExpressionVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( InfixExpression infixExpression )
    {
        new ExpressionVisitor( this.callbacks ).visit( infixExpression.getLeftOperand() );
        new ExpressionVisitor( this.callbacks ).visit( infixExpression.getRightOperand() );

        List<Expression> extendExpressions = infixExpression.extendedOperands();
        extendExpressions.stream().forEach( e -> new ExpressionVisitor( this.callbacks ).visit( e ) );

        return false;
    }
}
