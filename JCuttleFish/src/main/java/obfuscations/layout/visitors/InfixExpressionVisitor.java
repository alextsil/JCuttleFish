package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.InfixExpression;
import pojo.ObfuscationInfo;

import java.util.List;


public class InfixExpressionVisitor extends ASTVisitor
{

    private final ObfuscationInfo obfuscationInfo;

    public InfixExpressionVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( InfixExpression infixExpression )
    {
        new ExpressionVisitor( this.obfuscationInfo ).visit( infixExpression.getLeftOperand() );
        new ExpressionVisitor( this.obfuscationInfo ).visit( infixExpression.getRightOperand() );

        List<Expression> extendExpressions = infixExpression.extendedOperands();
        extendExpressions.stream().forEach( e -> new ExpressionVisitor( this.obfuscationInfo ).visit( e ) );

        return false;
    }
}
