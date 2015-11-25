package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;


public class ExpressionVisitor extends ASTVisitor
{

    private final ObfuscationInfo obfuscationInfo;

    public ExpressionVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean preVisit2 ( ASTNode expression )
    {
        int expressionNodeType = expression.getNodeType();
        if ( expressionNodeType == ASTNode.INFIX_EXPRESSION )
        {
            CastToAndVisit.infixExpression( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.PREFIX_EXPRESSION )
        {
            CastToAndVisit.prefixExpression( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.FIELD_ACCESS )

        {
            CastToAndVisit.fieldAccess( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.QUALIFIED_NAME )
        {
            CastToAndVisit.qualifiedName( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.SIMPLE_NAME )
        {
            CastToAndVisit.simpleName( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression )expression;
            new ExpressionVisitor( this.obfuscationInfo ).preVisit2( parenthesizedExpression.getExpression() );
        } else if ( expressionNodeType == ASTNode.METHOD_INVOCATION )
        {
            CastToAndVisit.methodInvocation( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.CLASS_INSTANCE_CREATION )
        {
            CastToAndVisit.classInstanceCreation( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.ARRAY_ACCESS )
        {
            CastToAndVisit.arrayAccess( expression, this.obfuscationInfo );
        } else
        {
            //throw new RuntimeException( "NOT" );
        }
        return false;
    }
}
