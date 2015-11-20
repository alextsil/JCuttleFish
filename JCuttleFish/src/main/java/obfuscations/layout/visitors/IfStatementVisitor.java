package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.*;


public class IfStatementVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public IfStatementVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( IfStatement node )
    {
        if ( node.getExpression().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) node.getExpression();
            MethodInvocationVisitor visitor = new MethodInvocationVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( methodInvocation );
        }

        //Visit ThenStatement
        Block thenStatements = ( Block ) node.getThenStatement();
        for ( Object statement : thenStatements.statements() )
        {
            StatementVisitor visitor = new StatementVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( ( Statement ) statement );
        }

        //Visit ElseStatement
        Block elseStatements = ( Block ) node.getElseStatement();
        if ( elseStatements != null )
        {
            for ( Object statement : elseStatements.statements() )
            {
                StatementVisitor visitor = new StatementVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( ( Statement ) statement );
            }
        }
        return false;
    }
}
