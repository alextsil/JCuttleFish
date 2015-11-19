package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import obfuscations.layout.visitors.thisify.ThisifyReturnStatementVisitor;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class StatementVisitor
{

    private enum Side
    {
        LEFT,
        RIGHT
    }


    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    private final Logger logger = LoggerFactory.getLogger( StatementVisitor.class );

    public StatementVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    public boolean visit ( Statement statement )
    {
        Expression expression = null;
        if ( statement.getNodeType() == ASTNode.EXPRESSION_STATEMENT )
        {
            expression = ( ( ExpressionStatement ) statement ).getExpression();
            if ( expression.getNodeType() == ASTNode.METHOD_INVOCATION )
            {
                MethodInvocation methodInvocation = ( MethodInvocation ) expression;
                MethodInvocationVisitor visitor = new MethodInvocationVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
                visitor.visit( methodInvocation );

                //Rename arguments
                List<Object> arguments = methodInvocation.arguments();
                ModifyAst.renameMethodInvocationArguments( arguments, originalVarSimpleName, obfuscatedVarName );
                MethodArgumentsVisitor visitor2 = new MethodArgumentsVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor2.visit( arguments );
            }

            if ( expression.getNodeType() == ASTNode.ASSIGNMENT )
            {
                Assignment assignment = ( Assignment ) expression;
                AssignmentVisitor visitor = new AssignmentVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( assignment );
            }
        } else if ( statement.getNodeType() == ASTNode.RETURN_STATEMENT )
        {
            expression = ( ( ReturnStatement ) statement ).getExpression();
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            expressionVisitor.preVisit2( expression );

            ThisifyReturnStatementVisitor thisifyReturnStatementVisitor = new ThisifyReturnStatementVisitor( this.ast, statement );
            thisifyReturnStatementVisitor.preVisit2( expression );
        } else if ( statement.getNodeType() == ASTNode.VARIABLE_DECLARATION_STATEMENT )
        {
            VariableDeclarationStatement vds = ( VariableDeclarationStatement ) statement;
            VariableDeclarationStatementVisitor visitor = new VariableDeclarationStatementVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( vds );
        } else if ( statement.getNodeType() == ASTNode.IF_STATEMENT )
        {
            IfStatement ifStatement = ( IfStatement ) statement;
            IfStatementVisitor visitor = new IfStatementVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( ifStatement );
        } else if ( statement.getNodeType() == ASTNode.ENHANCED_FOR_STATEMENT )
        {
            EnhancedForStatement enhancedForStatement = ( EnhancedForStatement ) statement;
            EnhancedForStatementVisitor visitor = new EnhancedForStatementVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( enhancedForStatement );
        } else
        {
            logger.debug( "Not mapped yet" );
        }
        return false;
    }
}
