package obfuscations.layout;

import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitSource;
import util.ApplyChanges;
import util.ObfuscatedNamesProvider;
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
            //replace "if" with "typedecl.resolvebindings().isClass()";
            TypeDeclaration typeDecl = ( TypeDeclaration ) cu.types().get( 0 );
            if ( typeDecl.getFields().length != 0 )
            {
                for ( FieldDeclaration fieldDeclaration : typeDecl.getFields() )
                {
                    //get(0) translates to get variable declaration fragment.
                    VariableDeclarationFragment originalVdf = ( VariableDeclarationFragment ) fieldDeclaration.fragments().get( 0 );

                    SimpleName originalVarSimpleName = originalVdf.getName();

                    String obfuscatedVarName = obfuscatedVariableNames.pollFirst();

                    for ( MethodDeclaration methodDeclaration : typeDecl.getMethods() )
                    {
                        //debug var
                        Block methodBody = methodDeclaration.getBody();

                        List<SingleVariableDeclaration> methodParameters = methodDeclaration.parameters();
                        if ( !methodParameters.isEmpty() )
                        {
                            methodParameters.stream()
                                    .filter( svd -> svd.getName().getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                    .forEach( svd -> svd.getName().setIdentifier( obfuscatedVarName ) );
                        }

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
                                    List<SimpleName> arguments = methodInvocation.arguments();
                                    arguments.stream()
                                            .filter( argument -> argument.getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                            .forEach( argument -> argument.setIdentifier( obfuscatedVarName ) );
                                }

                                if ( expression.getNodeType() == ASTNode.ASSIGNMENT )
                                {
                                    Assignment assignment = ( Assignment ) expression;
                                    if ( assignment.getLeftHandSide().getNodeType() == ASTNode.FIELD_ACCESS )
                                    {
                                        FieldAccess fieldAccess = ( FieldAccess ) assignment.getLeftHandSide();
                                        RenameVariables.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
                                    } else if ( assignment.getLeftHandSide().getNodeType() == ASTNode.SIMPLE_NAME )
                                    {
                                        SimpleName simpleName = ( SimpleName ) assignment.getLeftHandSide();
                                        RenameVariables.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                                    } else if ( assignment.getLeftHandSide().getNodeType() == ASTNode.ARRAY_ACCESS )
                                    {
                                        ArrayAccess arrayAccess = ( ArrayAccess ) assignment.getLeftHandSide();
                                        FieldAccess fieldAccess = ( FieldAccess ) arrayAccess.getArray();
                                        RenameVariables.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
                                    }

                                    if ( assignment.getRightHandSide().getNodeType() == ASTNode.SIMPLE_NAME )
                                    {
                                        SimpleName simpleName = ( SimpleName ) assignment.getRightHandSide();
                                        RenameVariables.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                                    } else if ( assignment.getRightHandSide().getNodeType() == ASTNode.METHOD_INVOCATION )
                                    {
                                        MethodInvocation methodInvocation = ( MethodInvocation ) assignment.getRightHandSide();

                                        SimpleName invocationExpression = ( SimpleName ) methodInvocation.getExpression();
                                        if ( invocationExpression.getIdentifier().equals( originalVarSimpleName.getIdentifier() ) )
                                        {
                                            invocationExpression.setIdentifier( obfuscatedVarName );
                                        }

                                        //Replace arguments
                                        RenameVariables.renameMethodInvocationArguments( methodInvocation.arguments(), originalVarSimpleName, obfuscatedVarName );
                                    }
                                }
                            } else if ( statement.getNodeType() == ASTNode.RETURN_STATEMENT )
                            {
                                expression = ( ( ReturnStatement ) statement ).getExpression();

                                if ( expression.getNodeType() == ASTNode.FIELD_ACCESS )
                                {
                                    FieldAccess fieldAccess = ( FieldAccess ) expression;
                                    RenameVariables.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
                                } else if ( expression.getNodeType() == ASTNode.SIMPLE_NAME )
                                {
                                    SimpleName simpleName = ( SimpleName ) expression;
                                    RenameVariables.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                                } else if ( expression.getNodeType() == ASTNode.INFIX_EXPRESSION )
                                {
                                    InfixExpression infixExpression = ( InfixExpression ) expression;
                                    if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.METHOD_INVOCATION )
                                    {
                                        MethodInvocation infixMethodInvocation = ( MethodInvocation ) infixExpression.getLeftOperand();
                                        if ( infixMethodInvocation.getExpression().getNodeType() == ASTNode.CLASS_INSTANCE_CREATION )
                                        {
                                            ClassInstanceCreation classInstanceCreation = ( ClassInstanceCreation ) infixMethodInvocation.getExpression();
                                            //Replace arguments
                                            RenameVariables.renameMethodInvocationArguments( classInstanceCreation.arguments(), originalVarSimpleName, obfuscatedVarName );
                                        }
                                    }
                                }
                            } else if ( statement.getNodeType() == ASTNode.VARIABLE_DECLARATION_STATEMENT )
                            {
                                logger.debug( "Not mapped yet" );
                                VariableDeclarationStatement vds = ( VariableDeclarationStatement ) statement;
                                VariableDeclarationFragment localVdf = ( VariableDeclarationFragment ) vds.fragments().get( 0 );
                                ClassInstanceCreation methodInvocation = ( ClassInstanceCreation ) localVdf.getInitializer();

                            } else
                            {
                                logger.debug( "Not mapped yet" );
                            }
                        }
                    }
                    //Change declaration name after modifying all usages.
                    originalVdf.getName().setIdentifier( obfuscatedVarName );
                }
            }
        }

        return ApplyChanges.apply( unitSource );
    }
}
