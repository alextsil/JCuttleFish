package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PrefixExpressionVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    private final Logger logger = LoggerFactory.getLogger( PrefixExpressionVisitor.class );

    public PrefixExpressionVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( PrefixExpression prefixExpression )
    {
        int prefixExpressionOperandNodeType = prefixExpression.getOperand().getNodeType();
        if ( prefixExpressionOperandNodeType == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) prefixExpression.getOperand();
            if ( methodInvocation.getExpression() != null )
            {
                MethodInvocationVisitor visitor = new MethodInvocationVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( methodInvocation );
            }
        } else if ( prefixExpressionOperandNodeType == ASTNode.QUALIFIED_NAME )
        {
            QualifiedName qualifiedName = ( QualifiedName ) prefixExpression.getOperand();
            QualifiedNameVisitor visitor = new QualifiedNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( qualifiedName );

            if ( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier().equals( obfuscatedVarName ) )
            {
                ModifyAst.thisifyName( this.ast, qualifiedName );
            }
        }
        return false;
    }
}
