package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CastToAndVisit;

import java.util.Collection;


public class PrefixExpressionVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;
    private final Logger logger = LoggerFactory.getLogger( PrefixExpressionVisitor.class );

    public PrefixExpressionVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( PrefixExpression prefixExpression )
    {
        int prefixExpressionOperandNodeType = prefixExpression.getOperand().getNodeType();
        if ( prefixExpressionOperandNodeType == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation )prefixExpression.getOperand();
            if ( methodInvocation.getExpression() != null )
            {
                CastToAndVisit.methodInvocation( methodInvocation, this.callbacks );
            }
        } else if ( prefixExpressionOperandNodeType == ASTNode.QUALIFIED_NAME )
        {
            CastToAndVisit.qualifiedName( prefixExpression.getOperand(), this.callbacks );
        }
        return false;
    }
}
