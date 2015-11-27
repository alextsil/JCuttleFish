package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.*;

import java.util.Collection;


public class FieldAccessVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public FieldAccessVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
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
                new SimpleNameVisitor( this.callbacks ).visit( simpleName );
            }
        } else
        {
//            ModifyAst.renameFieldAccessName( fieldAccess, this.obfuscationInfo.getOriginalVarSimpleName(),
//                    this.obfuscationInfo.getObfuscatedVarName() );
            //maybe register field access visitor for this case
        }
        return true;
    }
}
