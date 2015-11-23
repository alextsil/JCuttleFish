package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;


public class PrefixExpressionVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;
    private final Logger logger = LoggerFactory.getLogger( PrefixExpressionVisitor.class );

    public PrefixExpressionVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
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
                CastToAndVisit.methodInvocation( methodInvocation, this.obfuscationInfo );
            }
        } else if ( prefixExpressionOperandNodeType == ASTNode.QUALIFIED_NAME )
        {
            CastToAndVisit.qualifiedName( prefixExpression.getOperand(), this.obfuscationInfo );
        }
        return false;
    }
}
