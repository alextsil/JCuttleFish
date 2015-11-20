package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;


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
        if ( methodInvocation.getExpression() == null )
        {
            return false;
        }
        ExpressionVisitor visitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
        visitor.preVisit2( methodInvocation.getExpression() );

        return false;
    }
}
