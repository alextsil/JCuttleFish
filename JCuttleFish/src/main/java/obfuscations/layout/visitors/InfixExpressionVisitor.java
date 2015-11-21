package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;


public class InfixExpressionVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public InfixExpressionVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
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
                CastToAndVisit.methodInvocation( infixMethodInvocation, this.obfuscationInfo );
            }
        } else if ( operandNodeType == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) operand;
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.obfuscationInfo );
            expressionVisitor.preVisit2( parenthesizedExpression.getExpression() );
        } else if ( operandNodeType == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) operand;
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.obfuscationInfo.getOriginalVarSimpleName(),
                    this.obfuscationInfo.getObfuscatedVarName() );
            simpleNameVisitor.visit( simpleName );

            if ( simpleName.getIdentifier().equals( this.obfuscationInfo.getObfuscatedVarName() ) )
            {
                ModifyAst.thisifyName( this.obfuscationInfo.getAst(), simpleName );
            }
        } else if ( operandNodeType == ASTNode.FIELD_ACCESS )
        {
            CastToAndVisit.fieldAccess( operand, this.obfuscationInfo );
        } else if ( operandNodeType == ASTNode.PREFIX_EXPRESSION )
        {
            CastToAndVisit.prefixExpression( operand, this.obfuscationInfo );
        } else if ( operandNodeType == ASTNode.QUALIFIED_NAME )
        {
            CastToAndVisit.qualifiedName( operand, this.obfuscationInfo );
        }
        return true;
    }
}
