package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;


public class InfixExpressionVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private Statement statement;

    private AST ast;

    public InfixExpressionVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, Statement statement, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.statement = statement;
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
                if ( infixMethodInvocation.getExpression().getNodeType() == ASTNode.CLASS_INSTANCE_CREATION )
                {
                    ClassInstanceCreation classInstanceCreation = ( ClassInstanceCreation ) infixMethodInvocation.getExpression();
                    ModifyAst.renameMethodInvocationArguments( classInstanceCreation.arguments(), this.originalVarSimpleName, this.obfuscatedVarName );
                }
            }
        } else if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) infixExpression.getLeftOperand();
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.statement, this.ast );
            expressionVisitor.preVisit2( parenthesizedExpression.getExpression() );
        } else if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) infixExpression.getLeftOperand();
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            simpleNameVisitor.visit( simpleName );
        } else if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) infixExpression.getLeftOperand();
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            fieldAccessVisitor.visit( fieldAccess );
        }

        //Right
        if ( infixExpression.getRightOperand().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation infixMethodInvocation = ( MethodInvocation ) infixExpression.getRightOperand();
            if ( infixMethodInvocation.getExpression() != null )
            {
                if ( infixMethodInvocation.getExpression().getNodeType() == ASTNode.CLASS_INSTANCE_CREATION )
                {
                    ClassInstanceCreation classInstanceCreation = ( ClassInstanceCreation ) infixMethodInvocation.getExpression();
                    ModifyAst.renameMethodInvocationArguments( classInstanceCreation.arguments(), this.originalVarSimpleName, this.obfuscatedVarName );
                }
            }
        } else if ( infixExpression.getRightOperand().getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) infixExpression.getRightOperand();
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.statement, this.ast );
            expressionVisitor.preVisit2( parenthesizedExpression.getExpression() );
        } else if ( infixExpression.getRightOperand().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) infixExpression.getRightOperand();
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            simpleNameVisitor.visit( simpleName );
        } else if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) infixExpression.getLeftOperand();
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            fieldAccessVisitor.visit( fieldAccess );
        }

        return super.visit( infixExpression );
    }
}
