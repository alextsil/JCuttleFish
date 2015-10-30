package obfuscations.layout.visitors.thisify;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ThisifyReturnStatementVisitor extends ASTVisitor
{

    private AST ast;

    private Statement statement;

    private final Logger logger = LoggerFactory.getLogger( ThisifyReturnStatementVisitor.class );

    public ThisifyReturnStatementVisitor ( AST ast, Statement statement )
    {
        this.ast = ast;
        this.statement = statement;
    }

    @Override
    public boolean preVisit2 ( ASTNode node )
    {
        if ( node.getNodeType() == ASTNode.INFIX_EXPRESSION )
        {
            InfixExpression infixExpression = ( InfixExpression ) node;
            ThisifyInfixExpressionVisitor thisifyInfixExpressionVisitor = new ThisifyInfixExpressionVisitor( this.ast, this.statement );
            thisifyInfixExpressionVisitor.visit( infixExpression );
        } else if ( node.getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) node;
            logger.warn( "fieldAccess not mapped" );
        } else if ( node.getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) node;
            ModifyAst.thisifyStatement( this.ast, this.statement );
        } else if ( node.getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) node;
            ThisifyReturnStatementVisitor thisifyReturnStatementVisitor = new ThisifyReturnStatementVisitor( ast, statement );
            thisifyReturnStatementVisitor.preVisit2( parenthesizedExpression.getExpression() );
        }
        return true;
    }
}
