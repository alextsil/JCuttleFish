package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class InfixExpressionVisitor extends ASTVisitor
{

    private enum OperandType
    {
        LEFT,
        RIGHT,
        EXTRA
    }


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
        this.visitOperand( infixExpression.getLeftOperand(), infixExpression, OperandType.LEFT, -1 );
        this.visitOperand( infixExpression.getRightOperand(), infixExpression, OperandType.RIGHT, -1 );

        for ( int i = 0; i < infixExpression.extendedOperands().size(); i++ )
        {
            Expression expression = ( Expression ) infixExpression.extendedOperands().get( i );
            this.visitOperand( expression, infixExpression, OperandType.EXTRA, i );
        }
        return false;
    }

    //Inserting InfixExpression, operand type and position(only for extended operands) as a workaround to the thisifier
    //not being able to set the operand to the InfixExpression otherwise.HACK
    public boolean visitOperand ( Expression operand, InfixExpression expression, OperandType type, int pos )
    {
        if ( operand.getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation infixMethodInvocation = ( MethodInvocation ) operand;
            if ( infixMethodInvocation.getExpression() != null )
            {
                MethodInvocationExpressionVisitor visitor = new MethodInvocationExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( infixMethodInvocation );
            }
        } else if ( operand.getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) operand;
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            expressionVisitor.preVisit2( parenthesizedExpression.getExpression() );
        } else if ( operand.getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) operand;
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            simpleNameVisitor.visit( simpleName );

            if ( simpleName.getIdentifier().equals( obfuscatedVarName ) )
            {
                this.setNewOperand( ModifyAst.thisifySimpleName( this.ast, simpleName ), expression, type, pos );
            }
        } else if ( operand.getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) operand;
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            fieldAccessVisitor.visit( fieldAccess );
        } else if ( operand.getNodeType() == ASTNode.PREFIX_EXPRESSION )
        {
            PrefixExpression prefixExpression = ( PrefixExpression ) operand;
            PrefixExpressionVisitor visitor = new PrefixExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( prefixExpression );
        } else if ( operand.getNodeType() == ASTNode.QUALIFIED_NAME )
        {
            QualifiedName qualifiedName = ( QualifiedName ) operand;
            QualifiedNameVisitor visitor = new QualifiedNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( qualifiedName );

            if ( qualifiedName.getQualifier().getFullyQualifiedName().equals( obfuscatedVarName ) )
            {
                this.setNewOperand( ModifyAst.thisifyQualifiedName( this.ast, qualifiedName ), expression, type, pos );
            }
        }
        return true;
    }

    public void setNewOperand ( Expression operand, InfixExpression expression, OperandType type, int pos )
    {
        if ( type == OperandType.LEFT )
        {
            expression.setLeftOperand( operand );
        } else if ( type == OperandType.RIGHT )
        {
            expression.setRightOperand( operand );
        } else
        {
            //Operand is the thisified element.
            List<Expression> extendedOperands = expression.extendedOperands();
            extendedOperands.set( pos, operand );
        }
    }
}
