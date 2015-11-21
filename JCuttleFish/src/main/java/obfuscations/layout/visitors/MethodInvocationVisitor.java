package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import pojo.ObfuscationInfo;

import java.util.List;


public class MethodInvocationVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public MethodInvocationVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( MethodInvocation methodInvocation )
    {
        if ( methodInvocation.getExpression() != null )
        {
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.obfuscationInfo );
            expressionVisitor.preVisit2( methodInvocation.getExpression() );
        }

        //Rename and thisify arguments
        List<Object> arguments = methodInvocation.arguments();
        ModifyAst.renameMethodInvocationArguments( arguments, this.obfuscationInfo.getOriginalVarSimpleName(),
                this.obfuscationInfo.getObfuscatedVarName() );
        MethodArgumentsVisitor methodArgumentsVisitor = new MethodArgumentsVisitor( this.obfuscationInfo );
        methodArgumentsVisitor.visit( arguments );
        return false;
    }
}
