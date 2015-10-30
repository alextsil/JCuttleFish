package obfuscations.layout.visitors.thisify;

import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ThisifyInfixExpressionVisitor extends ASTVisitor
{

    private AST ast;

    private Statement statement;

    private final Logger logger = LoggerFactory.getLogger( ThisifyInfixExpressionVisitor.class );

    public ThisifyInfixExpressionVisitor ( AST ast, Statement statement )
    {
        this.ast = ast;
        this.statement = statement;
    }

    @Override
    public boolean visit ( InfixExpression node )
    {
        //Left
        if ( node.getLeftOperand().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            //call method expression thisify visitor
        } else if ( node.getLeftOperand().getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) node.getLeftOperand();
            ThisifyReturnStatementVisitor thisifyReturnStatementVisitor = new ThisifyReturnStatementVisitor( this.ast, this.statement );
            thisifyReturnStatementVisitor.preVisit2( parenthesizedExpression );
        } else if ( node.getLeftOperand().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) node.getLeftOperand();

            IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
            if ( varBinding.isField() )
            {
                FieldAccess fieldAccess = ast.newFieldAccess();
                fieldAccess.setExpression( ast.newThisExpression() );
                fieldAccess.setName( ast.newSimpleName( simpleName.getIdentifier() ) );
                node.setLeftOperand( fieldAccess );
            }
        }

        //Right
        if ( node.getRightOperand().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            //call method expression thisify visitor
        } else if ( node.getRightOperand().getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) node.getRightOperand();
            ThisifyReturnStatementVisitor thisifyReturnStatementVisitor = new ThisifyReturnStatementVisitor( this.ast, this.statement );
            thisifyReturnStatementVisitor.preVisit2( parenthesizedExpression );
        } else if ( node.getRightOperand().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) node.getRightOperand();

            IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
            if ( varBinding.isField() )
            {
                FieldAccess fieldAccess = ast.newFieldAccess();
                fieldAccess.setExpression( ast.newThisExpression() );
                fieldAccess.setName( ast.newSimpleName( simpleName.getIdentifier() ) );
                node.setRightOperand( fieldAccess );
            }
        }
        return super.visit( node );
    }
}
