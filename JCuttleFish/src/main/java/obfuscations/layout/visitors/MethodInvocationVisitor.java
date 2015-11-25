package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import pojo.ObfuscationInfo;

import java.util.List;


public class MethodInvocationVisitor extends ASTVisitor
{

    private final ObfuscationInfo obfuscationInfo;

    public MethodInvocationVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( MethodInvocation methodInvocation )
    {
        if ( methodInvocation.getExpression() != null )
        {
            new ExpressionVisitor( this.obfuscationInfo ).visit( methodInvocation.getExpression() );
        }

        //Rename and thisify arguments
        List<Object> arguments = methodInvocation.arguments();
        ModifyAst.renameMethodInvocationArguments( arguments, this.obfuscationInfo );
        new MethodArgumentsVisitor( this.obfuscationInfo ).visit( arguments );
        return false;
    }
}
