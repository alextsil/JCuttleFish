package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PrefixExpressionVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private Statement statement;

    private AST ast;

    private final Logger logger = LoggerFactory.getLogger( PrefixExpressionVisitor.class );

    public PrefixExpressionVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, Statement statement, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.statement = statement;
        this.ast = ast;
    }

    @Override
    public boolean visit ( PrefixExpression prefixExpression )
    {
        if ( prefixExpression.getOperand().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) prefixExpression.getOperand();
            if ( methodInvocation.getExpression() != null )
            {
                MethodInvocationExpressionVisitor visitor = new MethodInvocationExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( methodInvocation );
            }
        }
        return false;
    }
}
