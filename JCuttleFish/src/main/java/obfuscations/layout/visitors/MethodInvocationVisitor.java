package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

import java.util.List;


public class MethodInvocationVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public MethodInvocationVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( MethodInvocation methodInvocation )
    {
        if ( methodInvocation.getExpression() != null )
        {
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            expressionVisitor.preVisit2( methodInvocation.getExpression() );
        }

        //Rename and thisify arguments
        List<Object> arguments = methodInvocation.arguments();
        ModifyAst.renameMethodInvocationArguments( arguments, this.originalVarSimpleName, this.obfuscatedVarName );
        MethodArgumentsVisitor methodArgumentsVisitor = new MethodArgumentsVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
        methodArgumentsVisitor.visit( arguments );
        return false;
    }
}
