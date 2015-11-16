package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InfixExpressionVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    private final Logger logger = LoggerFactory.getLogger( InfixExpressionVisitor.class );

    public InfixExpressionVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( InfixExpression infixExpression )
    {
        //Left
        if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation infixMethodInvocation = ( MethodInvocation ) infixExpression.getLeftOperand();
            if ( infixMethodInvocation.getExpression() != null )
            {
                MethodInvocationExpressionVisitor visitor = new MethodInvocationExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( infixMethodInvocation );
            }
        } else if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) infixExpression.getLeftOperand();
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            expressionVisitor.preVisit2( parenthesizedExpression.getExpression() );
        } else if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) infixExpression.getLeftOperand();
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            simpleNameVisitor.visit( simpleName );
        } else if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) infixExpression.getLeftOperand();
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            fieldAccessVisitor.visit( fieldAccess );
        } else if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.PREFIX_EXPRESSION )
        {
            PrefixExpression prefixExpression = ( PrefixExpression ) infixExpression.getLeftOperand();
            PrefixExpressionVisitor visitor = new PrefixExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( prefixExpression );
        }

        //Right
        if ( infixExpression.getRightOperand().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation infixMethodInvocation = ( MethodInvocation ) infixExpression.getRightOperand();
            if ( infixMethodInvocation.getExpression() != null )
            {
                MethodInvocationExpressionVisitor visitor = new MethodInvocationExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( infixMethodInvocation );
            }
        } else if ( infixExpression.getRightOperand().getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) infixExpression.getRightOperand();
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            expressionVisitor.preVisit2( parenthesizedExpression.getExpression() );
        } else if ( infixExpression.getRightOperand().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) infixExpression.getRightOperand();
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            simpleNameVisitor.visit( simpleName );

            if ( simpleName.getIdentifier().equals( obfuscatedVarName ) )
            {
                infixExpression.setRightOperand( ModifyAst.thisifySimpleName( this.ast, simpleName ) );
            }
        } else if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) infixExpression.getLeftOperand();
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            fieldAccessVisitor.visit( fieldAccess );
        }

        return false;
    }
}
