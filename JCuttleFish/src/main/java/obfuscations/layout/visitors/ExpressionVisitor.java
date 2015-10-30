package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.*;


public class ExpressionVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private Statement statement;

    private AST ast;

    public ExpressionVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, Statement statement, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.statement = statement;
        this.ast = ast;
    }

    @Override
    //mono gia return_expression
    public boolean preVisit2 ( ASTNode expression )
    {
        int nodeType = expression.getNodeType();

        if ( expression.getNodeType() == ASTNode.INFIX_EXPRESSION )
        {
            InfixExpression infixExpression = ( InfixExpression ) expression;
            InfixExpressionVisitor infixExpressionVisitor = new InfixExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.statement, this.ast );
            infixExpressionVisitor.visit( infixExpression );
        } else if ( expression.getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) expression;
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            fieldAccessVisitor.visit( fieldAccess );
        } else if ( expression.getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) expression;
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            simpleNameVisitor.visit( simpleName );
        } else if ( expression.getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) expression;
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.statement, this.ast );
            expressionVisitor.preVisit2( parenthesizedExpression.getExpression() );
        }
        return true;
    }
}
