package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;


public class AssignmentVisitor extends ASTVisitor
{

    private enum Side
    {
        LEFT,
        RIGHT
    }


    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public AssignmentVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( Assignment assignment )
    {
        this.visitAssignmentSideExpression( assignment, assignment.getLeftHandSide(), Side.LEFT );
        this.visitAssignmentSideExpression( assignment, assignment.getRightHandSide(), Side.RIGHT );
        return false;
    }

    public void visitAssignmentSideExpression ( Assignment assignment, Expression expression, Side expressionSide )
    {
        int expressionNodeType = expression.getNodeType();

        if ( expressionNodeType == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) expression;
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            fieldAccessVisitor.visit( fieldAccess );
        } else if ( expressionNodeType == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) expression;
            IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
            if ( varBinding.isField() )
            {
                ModifyAst.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                ModifyAst.thisifySimpleName( this.ast, simpleName );
            }
        } else if ( expressionNodeType == ASTNode.ARRAY_ACCESS )
        {
            ArrayAccess arrayAccess = ( ArrayAccess ) expression;
            FieldAccess fieldAccess = ( FieldAccess ) arrayAccess.getArray();
            ModifyAst.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
        } else if ( expressionNodeType == ASTNode.QUALIFIED_NAME )
        {
            QualifiedName qualifiedName = ( QualifiedName ) expression;
            QualifiedNameVisitor qualifiedNameVisitor = new QualifiedNameVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            qualifiedNameVisitor.visit( qualifiedName );

            if ( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier().equals( obfuscatedVarName ) )
            {
                //TODO : remove by moving logic to thisifier
                this.setSideExpressionOnAssignment( assignment, ModifyAst.thisifyQualifiedName( this.ast, qualifiedName ), expressionSide );
            }
        } else if ( expressionNodeType == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) expression;
            SimpleName invocationExpression = ( SimpleName ) methodInvocation.getExpression();
            ModifyAst.renameSimpleName( invocationExpression, originalVarSimpleName, obfuscatedVarName );
            ModifyAst.renameMethodInvocationArguments( methodInvocation.arguments(), originalVarSimpleName, obfuscatedVarName );

        } else if ( expressionNodeType == ASTNode.INFIX_EXPRESSION )
        {
            InfixExpression infixExpression = ( InfixExpression ) expression;
            InfixExpressionVisitor visitor = new InfixExpressionVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( infixExpression );
        } else if ( expressionNodeType == ASTNode.PREFIX_EXPRESSION )
        {
            PrefixExpression prefixExpression = ( PrefixExpression ) expression;
            PrefixExpressionVisitor visitor = new PrefixExpressionVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( prefixExpression );
        }
    }

    @Deprecated
    public void setSideExpressionOnAssignment ( Assignment assignment, Expression expression, Side expressionSide )
    {
        if ( expressionSide == Side.LEFT )
        {
            assignment.setLeftHandSide( expression );
        } else
        {
            assignment.setRightHandSide( expression );
        }
    }
}
