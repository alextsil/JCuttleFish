package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StatementVisitor
{

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
        Expression expression;
        int statementNodeType = statement.getNodeType();

        if ( statementNodeType == ASTNode.EXPRESSION_STATEMENT )
        {
            expression = ( ( ExpressionStatement ) statement ).getExpression();
            int expressionNodeType = expression.getNodeType();
            if ( expressionNodeType == ASTNode.METHOD_INVOCATION )
            {
                MethodInvocation methodInvocation = ( MethodInvocation ) expression;
                MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
                methodInvocationVisitor.visit( methodInvocation );
            }

            if ( expressionNodeType == ASTNode.ASSIGNMENT )
            {
                Assignment assignment = ( Assignment ) expression;
                AssignmentVisitor assignmentVisitor = new AssignmentVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                assignmentVisitor.visit( assignment );
            }
        } else if ( statementNodeType == ASTNode.RETURN_STATEMENT )
        {
            expression = ( ( ReturnStatement ) statement ).getExpression();
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            expressionVisitor.preVisit2( expression );
        } else if ( statementNodeType == ASTNode.VARIABLE_DECLARATION_STATEMENT )
        {
            VariableDeclarationStatement vds = ( VariableDeclarationStatement ) statement;
            VariableDeclarationStatementVisitor visitor = new VariableDeclarationStatementVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( vds );
        } else if ( statementNodeType == ASTNode.IF_STATEMENT )
        {
            IfStatement ifStatement = ( IfStatement ) statement;
            IfStatementVisitor visitor = new IfStatementVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( ifStatement );
        } else if ( statementNodeType == ASTNode.ENHANCED_FOR_STATEMENT )
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
