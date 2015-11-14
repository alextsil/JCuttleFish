package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
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
                if ( methodInvocation.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
                {
                    SimpleName simpleName = ( SimpleName ) methodInvocation.getExpression();
                    SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
                    simpleNameVisitor.visit( simpleName );

                    if ( simpleName.getIdentifier().equals( obfuscatedVarName ) )
                    {
                        ModifyAst.thisifyMethodInvocationSimpleName( this.ast, methodInvocation, simpleName );
                    }
                }
            }
        }
        return false;
    }
}
