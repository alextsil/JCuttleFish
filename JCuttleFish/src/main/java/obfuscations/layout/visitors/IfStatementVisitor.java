package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;

import java.util.List;


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
            MethodInvocationExpressionVisitor visitor = new MethodInvocationExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( methodInvocation );

            //Rename arguments
            List<Object> arguments = methodInvocation.arguments();
            ModifyAst.renameMethodInvocationArguments( arguments, this.originalVarSimpleName, this.obfuscatedVarName );
            MethodArgumentsVisitor visitor2 = new MethodArgumentsVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor2.visit( arguments );
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
