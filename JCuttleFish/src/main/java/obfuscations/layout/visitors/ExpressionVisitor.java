package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import util.CastToAndVisit;

import java.util.Collection;


public class ExpressionVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public ExpressionVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    public boolean visit ( ASTNode expression )
    {
        this.callbacks.stream().forEach( c -> c.notify( expression ) );

        int expressionNodeType = expression.getNodeType();
        if ( expressionNodeType == ASTNode.INFIX_EXPRESSION )
        {
            CastToAndVisit.infixExpression( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.PREFIX_EXPRESSION )
        {
            CastToAndVisit.prefixExpression( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.FIELD_ACCESS )

        {
            CastToAndVisit.fieldAccess( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.QUALIFIED_NAME )
        {
            CastToAndVisit.qualifiedName( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.SIMPLE_NAME )
        {
            CastToAndVisit.simpleName( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression )expression;
            new ExpressionVisitor( this.callbacks ).visit( parenthesizedExpression.getExpression() );
        } else if ( expressionNodeType == ASTNode.METHOD_INVOCATION )
        {
            CastToAndVisit.methodInvocation( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.CLASS_INSTANCE_CREATION )
        {
            CastToAndVisit.classInstanceCreation( expression, this.callbacks );
        } else if ( expressionNodeType == ASTNode.ARRAY_ACCESS )
        {
            CastToAndVisit.arrayAccess( expression, this.callbacks );
        } else
        {
            //throw new RuntimeException( "NOT" );
        }
        return false;
    }
}
