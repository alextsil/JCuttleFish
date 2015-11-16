package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import obfuscations.layout.visitors.thisify.ThisifyReturnStatementVisitor;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class StatementVisitor
{

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
                MethodArgumentsVisitor visitor2 = new MethodArgumentsVisitor( obfuscatedVarName, this.ast );
                visitor2.visit( arguments );
            }

            if ( expression.getNodeType() == ASTNode.ASSIGNMENT )
            {
                Assignment assignment = ( Assignment ) expression;
                if ( assignment.getLeftHandSide().getNodeType() == ASTNode.FIELD_ACCESS )
                {
                    FieldAccess fieldAccess = ( FieldAccess ) assignment.getLeftHandSide();
                    ModifyAst.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
                } else if ( assignment.getLeftHandSide().getNodeType() == ASTNode.SIMPLE_NAME )
                {
                    SimpleName simpleName = ( SimpleName ) assignment.getLeftHandSide();
                    IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
                    if ( varBinding.isField() )
                    {
                        ModifyAst.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                        assignment.setLeftHandSide( ModifyAst.thisifySimpleName( this.ast, simpleName ) );
                    }
                } else if ( assignment.getLeftHandSide().getNodeType() == ASTNode.ARRAY_ACCESS )
                {
                    ArrayAccess arrayAccess = ( ArrayAccess ) assignment.getLeftHandSide();
                    FieldAccess fieldAccess = ( FieldAccess ) arrayAccess.getArray();
                    ModifyAst.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
                } else if ( assignment.getLeftHandSide().getNodeType() == ASTNode.QUALIFIED_NAME )
                {
                    QualifiedName qualifiedName = ( QualifiedName ) assignment.getLeftHandSide();
                    QualifiedNameVisitor qualifiedNameVisitor = new QualifiedNameVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
                    qualifiedNameVisitor.visit( qualifiedName );

                    if ( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier().equals( obfuscatedVarName ) )
                    {
                        assignment.setLeftHandSide( ModifyAst.thisifyQualifiedName( this.ast, qualifiedName ) );
                    }
                }

                if ( assignment.getRightHandSide().getNodeType() == ASTNode.SIMPLE_NAME )
                {
                    SimpleName simpleName = ( SimpleName ) assignment.getRightHandSide();
                    IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
                    if ( varBinding.isField() )
                    {
                        ModifyAst.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                    }
                } else if ( assignment.getRightHandSide().getNodeType() == ASTNode.FIELD_ACCESS )
                {
                    FieldAccess fieldAccess = ( FieldAccess ) assignment.getRightHandSide();
                    FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( originalVarSimpleName, obfuscatedVarName, this.ast );
                    fieldAccessVisitor.visit( fieldAccess );
                } else if ( assignment.getRightHandSide().getNodeType() == ASTNode.METHOD_INVOCATION )
                {
                    MethodInvocation methodInvocation = ( MethodInvocation ) assignment.getRightHandSide();
                    SimpleName invocationExpression = ( SimpleName ) methodInvocation.getExpression();
                    ModifyAst.renameSimpleName( invocationExpression, originalVarSimpleName, obfuscatedVarName );
                    ModifyAst.renameMethodInvocationArguments( methodInvocation.arguments(), originalVarSimpleName, obfuscatedVarName );

                } else if ( assignment.getRightHandSide().getNodeType() == ASTNode.INFIX_EXPRESSION )
                {
                    InfixExpression infixExpression = ( InfixExpression ) assignment.getRightHandSide();
                    InfixExpressionVisitor visitor = new InfixExpressionVisitor( originalVarSimpleName, obfuscatedVarName, statement, this.ast );
                    visitor.visit( infixExpression );
                } else if ( assignment.getRightHandSide().getNodeType() == ASTNode.PREFIX_EXPRESSION )
                {
                    PrefixExpression prefixExpression = ( PrefixExpression ) assignment.getRightHandSide();
                    PrefixExpressionVisitor visitor = new PrefixExpressionVisitor( originalVarSimpleName, obfuscatedVarName, statement, this.ast );
                    visitor.visit( prefixExpression );
                }
            }
        } else if ( statement.getNodeType() == ASTNode.RETURN_STATEMENT )
        {
            expression = ( ( ReturnStatement ) statement ).getExpression();
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( originalVarSimpleName, obfuscatedVarName, statement, this.ast );
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
        } else
        {
            logger.debug( "Not mapped yet" );
        }
        return false;
    }
}
