package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MethodInvocationExpressionVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    private final Logger logger = LoggerFactory.getLogger( MethodInvocationExpressionVisitor.class );

    public MethodInvocationExpressionVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( MethodInvocation methodInvocation )
    {
        if ( methodInvocation.getExpression() == null )
        {
            return false;
        }
        if ( methodInvocation.getExpression().getNodeType() == ASTNode.CLASS_INSTANCE_CREATION )
        {
            ClassInstanceCreation classInstanceCreation = ( ClassInstanceCreation ) methodInvocation.getExpression();
            ModifyAst.renameMethodInvocationArguments( classInstanceCreation.arguments(), this.originalVarSimpleName, this.obfuscatedVarName );
        } else if ( methodInvocation.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) methodInvocation.getExpression();
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            simpleNameVisitor.visit( simpleName );

            if ( simpleName.getIdentifier().equals( obfuscatedVarName ) )
            {
                methodInvocation.setExpression( ModifyAst.thisifySimpleName( this.ast, simpleName ) );
            }
        }
        return false;
    }
}
