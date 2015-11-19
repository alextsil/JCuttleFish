package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import obfuscations.layout.visitors.thisify.ThisifyReturnStatementVisitor;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class StatementVisitor
{

    private enum Side
    {
        LEFT,
        RIGHT
    }


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
        Expression expression = null;
        if ( statement.getNodeType() == ASTNode.EXPRESSION_STATEMENT )
        {
            expression = ( ( ExpressionStatement ) statement ).getExpression();
            if ( expression.getNodeType() == ASTNode.METHOD_INVOCATION )
            {
                MethodInvocation methodInvocation = ( MethodInvocation ) expression;
                MethodInvocationExpressionVisitor visitor = new MethodInvocationExpressionVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
                visitor.visit( methodInvocation );

                //Rename arguments
                List<Object> arguments = methodInvocation.arguments();
                ModifyAst.renameMethodInvocationArguments( arguments, originalVarSimpleName, obfuscatedVarName );
                MethodArgumentsVisitor visitor2 = new MethodArgumentsVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor2.visit( arguments );
            }

            if ( expression.getNodeType() == ASTNode.ASSIGNMENT )
            {
                Assignment assignment = ( Assignment ) expression;
                this.visitAssignmentSideExpression( assignment, assignment.getLeftHandSide(), Side.LEFT );
                this.visitAssignmentSideExpression( assignment, assignment.getRightHandSide(), Side.RIGHT );
            }
        } else if ( statement.getNodeType() == ASTNode.RETURN_STATEMENT )
        {
            expression = ( ( ReturnStatement ) statement ).getExpression();
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            expressionVisitor.preVisit2( expression );

            ThisifyReturnStatementVisitor thisifyReturnStatementVisitor = new ThisifyReturnStatementVisitor( this.ast, statement );
            thisifyReturnStatementVisitor.preVisit2( expression );
        } else if ( statement.getNodeType() == ASTNode.VARIABLE_DECLARATION_STATEMENT )
        {
            VariableDeclarationStatement vds = ( VariableDeclarationStatement ) statement;
            VariableDeclarationStatementVisitor visitor = new VariableDeclarationStatementVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( vds );
        } else if ( statement.getNodeType() == ASTNode.IF_STATEMENT )
        {
            IfStatement ifStatement = ( IfStatement ) statement;
            IfStatementVisitor visitor = new IfStatementVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( ifStatement );
        } else if ( statement.getNodeType() == ASTNode.ENHANCED_FOR_STATEMENT )
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

    //TODO : Try to decouple it from the checks and use ExpressionVisitor instead of this function.
    public void visitAssignmentSideExpression ( Assignment assignment, Expression expression, Side expressionSide )
    {
        if ( expression.getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) expression;
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            fieldAccessVisitor.visit( fieldAccess );
        } else if ( expression.getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) expression;
            IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
            if ( varBinding.isField() )
            {
                ModifyAst.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                assignment.setLeftHandSide( ModifyAst.thisifySimpleName( this.ast, simpleName ) );
                this.setSideExpressionOnAssignment( assignment, ModifyAst.thisifySimpleName( this.ast, simpleName ), expressionSide );
            }
        } else if ( expression.getNodeType() == ASTNode.ARRAY_ACCESS )
        {
            ArrayAccess arrayAccess = ( ArrayAccess ) expression;
            FieldAccess fieldAccess = ( FieldAccess ) arrayAccess.getArray();
            ModifyAst.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
        } else if ( expression.getNodeType() == ASTNode.QUALIFIED_NAME )
        {
            QualifiedName qualifiedName = ( QualifiedName ) expression;
            QualifiedNameVisitor qualifiedNameVisitor = new QualifiedNameVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            qualifiedNameVisitor.visit( qualifiedName );

            if ( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier().equals( obfuscatedVarName ) )
            {
                this.setSideExpressionOnAssignment( assignment, ModifyAst.thisifyQualifiedName( this.ast, qualifiedName ), expressionSide );
            }
        } else if ( expression.getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) expression;
            SimpleName invocationExpression = ( SimpleName ) methodInvocation.getExpression();
            ModifyAst.renameSimpleName( invocationExpression, originalVarSimpleName, obfuscatedVarName );
            ModifyAst.renameMethodInvocationArguments( methodInvocation.arguments(), originalVarSimpleName, obfuscatedVarName );

        } else if ( expression.getNodeType() == ASTNode.INFIX_EXPRESSION )
        {
            InfixExpression infixExpression = ( InfixExpression ) expression;
            InfixExpressionVisitor visitor = new InfixExpressionVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( infixExpression );
        } else if ( expression.getNodeType() == ASTNode.PREFIX_EXPRESSION )
        {
            PrefixExpression prefixExpression = ( PrefixExpression ) expression;
            PrefixExpressionVisitor visitor = new PrefixExpressionVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
            visitor.visit( prefixExpression );
        }
    }

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
