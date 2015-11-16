package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;


public class FieldAccessVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public FieldAccessVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( FieldAccess fieldAccess )
    {
        if ( fieldAccess.getExpression().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) fieldAccess.getExpression();
            if ( methodInvocation.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
            {
                ModifyAst.renameSimpleName( ( SimpleName ) methodInvocation.getExpression(), this.originalVarSimpleName, this.obfuscatedVarName );
                //TODO : use a visitor to choose which thisify function will be used.
                methodInvocation.setExpression( ModifyAst.thisifySimpleName( this.ast, ( SimpleName ) methodInvocation.getExpression() ) );
            }
        } else
        {
            ModifyAst.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
        }
        return true;
    }
}
