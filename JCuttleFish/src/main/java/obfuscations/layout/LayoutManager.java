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
            TypeDeclaration typeDecl = ( TypeDeclaration ) cu.types().get( 0 );
            if ( typeDecl.resolveBinding().isClass() )
            {
                for ( FieldDeclaration fieldDeclaration : typeDecl.getFields() )
                {
                    //get(0) translates to get variable declaration fragment.
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
                                        if ( methodInvocation.getExpression().getNodeType() == ASTNode.SIMPLE_NAME ) //Means the line is: Class (no this)
                                        {
                                            //Rename class' name
                                            SimpleName simpleName = ( SimpleName ) methodInvocation.getExpression();
                                            ModifyAst.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                                            ModifyAst.thisifyMethodInvocationSimpleName( cu.getAST(), methodInvocation, simpleName );
                                        } else if ( methodInvocation.getExpression().getNodeType() == ASTNode.FIELD_ACCESS ) //Means the line is: this.Class
                                        {
                                            FieldAccess fieldAccess = ( FieldAccess ) methodInvocation.getExpression();
                                            ModifyAst.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
                                        }
                                    }

                                    //Rename arguments
                                    List<? extends ASTNode> arguments = methodInvocation.arguments();
                                    ModifyAst.renameMethodInvocationArguments( arguments, originalVarSimpleName, obfuscatedVarName );
                                    ModifyAst.thisifyMethodInvocationArguments( cu.getAST(), methodInvocation );
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
                                    }

                                    if ( assignment.getRightHandSide().getNodeType() == ASTNode.SIMPLE_NAME )
                                    {
                                        SimpleName simpleName = ( SimpleName ) assignment.getRightHandSide();
                                        IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
                                        if ( varBinding.isField() )
                                        {
                                            ModifyAst.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                                        }
                                    } else if ( assignment.getRightHandSide().getNodeType() == ASTNode.METHOD_INVOCATION )
                                    {
                                        MethodInvocation methodInvocation = ( MethodInvocation ) assignment.getRightHandSide();

                                        SimpleName invocationExpression = ( SimpleName ) methodInvocation.getExpression();
                                        ModifyAst.renameSimpleName( invocationExpression, originalVarSimpleName, obfuscatedVarName );
                                        ModifyAst.renameMethodInvocationArguments( methodInvocation.arguments(), originalVarSimpleName, obfuscatedVarName );

                                    }
                                }
                            } else if ( statement.getNodeType() == ASTNode.RETURN_STATEMENT )
                            {
                                expression = ( ( ReturnStatement ) statement ).getExpression();
                                if ( expression.getNodeType() == ASTNode.FIELD_ACCESS )
                                {
                                    FieldAccess fieldAccess = ( FieldAccess ) expression;
                                    ModifyAst.renameFieldAccessName( fieldAccess, originalVarSimpleName, obfuscatedVarName );
                                } else if ( expression.getNodeType() == ASTNode.SIMPLE_NAME )
                                {
                                    SimpleName simpleName = ( SimpleName ) expression;
                                    IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
                                    if ( varBinding.isField() )
                                    {
                                        ModifyAst.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                                        ModifyAst.thisifyStatement( cu.getAST(), statement );
                                    }
                                } else if ( expression.getNodeType() == ASTNode.INFIX_EXPRESSION )
                                {
                                    InfixExpression infixExpression = ( InfixExpression ) expression;
                                    if ( infixExpression.getLeftOperand().getNodeType() == ASTNode.METHOD_INVOCATION )
                                    {
                                        MethodInvocation infixMethodInvocation = ( MethodInvocation ) infixExpression.getLeftOperand();
                                        if ( infixMethodInvocation.getExpression().getNodeType() == ASTNode.CLASS_INSTANCE_CREATION )
                                        {
                                            ClassInstanceCreation classInstanceCreation = ( ClassInstanceCreation ) infixMethodInvocation.getExpression();
                                            ModifyAst.renameMethodInvocationArguments( classInstanceCreation.arguments(), originalVarSimpleName, obfuscatedVarName );
                                        }
                                    }
                                }
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
