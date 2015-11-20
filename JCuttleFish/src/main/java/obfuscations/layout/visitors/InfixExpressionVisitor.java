package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;


public class InfixExpressionVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public InfixExpressionVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( InfixExpression infixExpression )
    {
        this.visitOperand( infixExpression.getLeftOperand() );
        this.visitOperand( infixExpression.getRightOperand() );

        for ( int i = 0; i < infixExpression.extendedOperands().size(); i++ )
        {
            Expression expression = ( Expression ) infixExpression.extendedOperands().get( i );
            this.visitOperand( expression );
        }
        return false;
    }

    //Inserting InfixExpression, operand type and position(only for extended operands) as a workaround to the thisifier
    //not being able to set the operand to the InfixExpression otherwise.HACK
    public boolean visitOperand ( Expression operand )
    {
        int operandNodeType = operand.getNodeType();
        if ( operandNodeType == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation infixMethodInvocation = ( MethodInvocation ) operand;
            if ( infixMethodInvocation.getExpression() != null )
            {
                MethodInvocationVisitor visitor = new MethodInvocationVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( infixMethodInvocation );
            }
        } else if ( operandNodeType == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) operand;
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            expressionVisitor.preVisit2( parenthesizedExpression.getExpression() );
        } else if ( operandNodeType == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) operand;
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            simpleNameVisitor.visit( simpleName );

            if ( simpleName.getIdentifier().equals( obfuscatedVarName ) )
            {
                ModifyAst.thisifyName( this.ast, simpleName );
            }
        } else if ( operandNodeType == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) operand;
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            fieldAccessVisitor.visit( fieldAccess );
        } else if ( operandNodeType == ASTNode.PREFIX_EXPRESSION )
        {
            PrefixExpression prefixExpression = ( PrefixExpression ) operand;
            PrefixExpressionVisitor visitor = new PrefixExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( prefixExpression );
        } else if ( operandNodeType == ASTNode.QUALIFIED_NAME )
        {
            QualifiedName qualifiedName = ( QualifiedName ) operand;
            QualifiedNameVisitor visitor = new QualifiedNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( qualifiedName );

            if ( qualifiedName.getQualifier().getFullyQualifiedName().equals( obfuscatedVarName ) )
            {
                ModifyAst.thisifyName( this.ast, qualifiedName );
            }
        }
        return true;
    }
}
