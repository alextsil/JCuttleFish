package obfuscations.layout;

import obfuscations.layout.visitors.*;
import obfuscations.layout.visitors.thisify.ThisifyReturnStatementVisitor;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitSource;
import providers.ObfuscatedNamesProvider;
import util.ApplyChanges;
import util.ConvenienceWrappers;
import util.enums.ObfuscatedNamesVariations;

import java.util.Deque;
import java.util.List;


public class LayoutManager
{

    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public UnitSource obfuscate ( UnitSource unitSource )
    {
        CompilationUnit cu = unitSource.getCompilationUnit();
        cu.recordModifications();
        ObfuscatedNamesProvider obfNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedVariableNames = obfNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        if ( !cu.types().isEmpty() )
        {
            TypeDeclaration typeDecl = ( TypeDeclaration ) cu.types().get( 0 );
            if ( typeDecl.resolveBinding().isClass() )
            {
                for ( FieldDeclaration fieldDeclaration : ConvenienceWrappers.getPrivateFieldDeclarations( typeDecl ) )
                {
                    VariableDeclarationFragment originalVdf = ( VariableDeclarationFragment ) fieldDeclaration.fragments().get( 0 );
                    SimpleName originalVarSimpleName = originalVdf.getName();
                    String obfuscatedVarName = obfuscatedVariableNames.pollFirst();

                    for ( MethodDeclaration methodDeclaration : typeDecl.getMethods() )
                    {
                        List<Statement> statements = methodDeclaration.getBody().statements();
                        for ( Statement statement : statements )
                        {
                            Expression expression = null;
                            if ( statement.getNodeType() == ASTNode.EXPRESSION_STATEMENT )
                            {
                                expression = ( ( ExpressionStatement ) statement ).getExpression();
                                if ( expression.getNodeType() == ASTNode.METHOD_INVOCATION )
                                {
                                    MethodInvocation methodInvocation = ( MethodInvocation ) expression;
                                    //TODO : Move this chunk to ModifyAst class
                                    if ( methodInvocation.getExpression() != null )
                                    {
                                        if ( methodInvocation.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
                                        {
                                            //Rename class' name
                                            SimpleName simpleName = ( SimpleName ) methodInvocation.getExpression();
                                            ModifyAst.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                                            ModifyAst.thisifyMethodInvocationSimpleName( cu.getAST(), methodInvocation, simpleName );
                                        } else if ( methodInvocation.getExpression().getNodeType() == ASTNode.FIELD_ACCESS )
                                        {
                                            FieldAccess fieldAccess = ( FieldAccess ) methodInvocation.getExpression();
                                            ModifyAst.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
                                        }
                                    }

                                    //Rename arguments
                                    List<Object> arguments = methodInvocation.arguments();
                                    ModifyAst.renameMethodInvocationArguments( arguments, originalVarSimpleName, obfuscatedVarName );
                                    MethodArgumentsVisitor visitor = new MethodArgumentsVisitor( obfuscatedVarName, cu.getAST() );
                                    visitor.visit( arguments );
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
                                            ModifyAst.thisifyAssignmentSimpleName( cu.getAST(), assignment, simpleName );
                                        }
                                    } else if ( assignment.getLeftHandSide().getNodeType() == ASTNode.ARRAY_ACCESS )
                                    {
                                        ArrayAccess arrayAccess = ( ArrayAccess ) assignment.getLeftHandSide();
                                        FieldAccess fieldAccess = ( FieldAccess ) arrayAccess.getArray();
                                        ModifyAst.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
                                    } else if ( assignment.getLeftHandSide().getNodeType() == ASTNode.QUALIFIED_NAME )
                                    {
                                        QualifiedName qualifiedName = ( QualifiedName ) assignment.getLeftHandSide();
                                        QualifiedNameVisitor qualifiedNameVisitor = new QualifiedNameVisitor( originalVarSimpleName, obfuscatedVarName, cu.getAST(), assignment );
                                        qualifiedNameVisitor.visit( qualifiedName );
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
                                        FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( originalVarSimpleName, obfuscatedVarName, cu.getAST() );
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
                                        InfixExpressionVisitor visitor = new InfixExpressionVisitor( originalVarSimpleName, obfuscatedVarName, statement, cu.getAST() );
                                        visitor.visit( infixExpression );
                                    } else if ( assignment.getRightHandSide().getNodeType() == ASTNode.PREFIX_EXPRESSION )
                                    {
                                        PrefixExpression prefixExpression = ( PrefixExpression ) assignment.getRightHandSide();
                                        PrefixExpressionVisitor visitor = new PrefixExpressionVisitor( originalVarSimpleName, obfuscatedVarName, statement, cu.getAST() );
                                        visitor.visit( prefixExpression );
                                    }
                                }
                            } else if ( statement.getNodeType() == ASTNode.RETURN_STATEMENT )
                            {
                                expression = ( ( ReturnStatement ) statement ).getExpression();
                                ExpressionVisitor expressionVisitor = new ExpressionVisitor( originalVarSimpleName, obfuscatedVarName, statement, cu.getAST() );
                                expressionVisitor.preVisit2( expression );

                                ThisifyReturnStatementVisitor thisifyReturnStatementVisitor = new ThisifyReturnStatementVisitor( cu.getAST(), statement );
                                thisifyReturnStatementVisitor.preVisit2( expression );

                            } else if ( statement.getNodeType() == ASTNode.VARIABLE_DECLARATION_STATEMENT )
                            {
                                //List<VariableDeclarationFragment> variableDeclarationFragments = ( ( VariableDeclarationStatement ) statement ).fragments();
                            } else
                            {
                                logger.debug( "Not mapped yet" );
                            }
                        }
                    }
                    //Change declaration name after modifying all usages.
                    originalVdf.getName().setIdentifier( obfuscatedVarName );
                }
            } else
            {
                throw new RuntimeException( "Bad input. isClass returned false." );
            }
        }
        //TODO: Move "apply" to ObfuscationCoordinator
        return ApplyChanges.apply( unitSource );
    }
}
