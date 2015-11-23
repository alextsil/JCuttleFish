package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import pojo.ObfuscationInfo;


public class FieldAccessVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public FieldAccessVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( FieldAccess fieldAccess )
    {
        if ( fieldAccess.getExpression().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation )fieldAccess.getExpression();
            if ( methodInvocation.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
            {
                SimpleName simpleName = ( SimpleName )methodInvocation.getExpression();
                ModifyAst.renameSimpleName( ( SimpleName )methodInvocation.getExpression(), this.obfuscationInfo.getOriginalVarSimpleName(),
                        this.obfuscationInfo.getObfuscatedVarName() );
                if ( simpleName.getIdentifier().equals( this.obfuscationInfo.getObfuscatedVarName() ) )
                {
                    ModifyAst.thisifyName( this.obfuscationInfo.getAst(), ( SimpleName )methodInvocation.getExpression() );
                }
            }
        } else
        {
            ModifyAst.renameFieldAccessName( fieldAccess, this.obfuscationInfo.getOriginalVarSimpleName(),
                    this.obfuscationInfo.getObfuscatedVarName() );
        }
        return true;
    }
}
