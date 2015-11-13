package obfuscations.layout;

import org.eclipse.jdt.core.dom.*;
import util.OptionalUtils;

import java.util.List;
import java.util.Optional;


public class ModifyAst
{

    /**
     * Compares the given (method's) arguments to the given SimpleName and replaces the match found with obfuscatedName parameter.
     *
     * @param arguments      The list of arguments to search into
     * @param originalName   The SimpleName to search for
     * @param obfuscatedName The String to replace a match
     */
    public static void renameMethodInvocationArguments ( List<? extends ASTNode> arguments, SimpleName originalName, String obfuscatedName )
    {
        for ( ASTNode argument : arguments )
        {
            if ( argument.getNodeType() == ASTNode.SIMPLE_NAME )
            {
                SimpleName simpleName = ( SimpleName ) argument;
                Optional<IVariableBinding> optionalIvb = OptionalUtils.getIVariableBinding( simpleName );
                if ( simpleName.getIdentifier().equals( originalName.getIdentifier() )
                        && optionalIvb.map( i -> i.isField() ).orElse( false ) )
                {
                    simpleName.setIdentifier( obfuscatedName );
                }
            }
            if ( argument.getNodeType() == ASTNode.FIELD_ACCESS )
            {
                FieldAccess fieldAccess = ( FieldAccess ) argument;
                //Might need the optionalIvb to check if it's really a local field.
                //Optional<IVariableBinding> optionalIvb = OptionalUtils.getIVariableBinding( fieldAccess );

                if ( fieldAccess.getName().getIdentifier().equals( originalName.getIdentifier() ) )
                {
                    fieldAccess.getName().setIdentifier( obfuscatedName );
                }
            }
        }
    }

    public static void renameFieldAccessName ( FieldAccess fieldAccess, SimpleName originalName, String obfuscatedVarName )
    {
        if ( fieldAccess.getName().getIdentifier().equals( originalName.getIdentifier() ) )
        {
            fieldAccess.getName().setIdentifier( obfuscatedVarName );
        }
    }

    public static void renameSimpleName ( SimpleName simpleName, SimpleName originalName, String obfuscatedVarName )
    {
        if ( simpleName.getIdentifier().equals( originalName.getIdentifier() ) )
        {
            simpleName.setIdentifier( obfuscatedVarName );
        }
    }

    public static void thisifyAssignmentSimpleName ( AST ast, Assignment assignment, SimpleName simpleName )
    {
        FieldAccess fieldAccess = ast.newFieldAccess();
        fieldAccess.setExpression( ast.newThisExpression() );
        fieldAccess.setName( ast.newSimpleName( simpleName.getIdentifier() ) );
        assignment.setLeftHandSide( fieldAccess );
    }

    public static void thisifyMethodInvocationSimpleName ( AST ast, MethodInvocation methodInvocation, SimpleName simpleName )
    {
        FieldAccess fieldAccess = ast.newFieldAccess();
        fieldAccess.setExpression( ast.newThisExpression() );
        fieldAccess.setName( ast.newSimpleName( simpleName.getIdentifier() ) );
        methodInvocation.setExpression( fieldAccess );
    }

    public static void thisifyStatement ( AST ast, Statement statement )
    {
        if ( statement.getNodeType() == ASTNode.RETURN_STATEMENT )
        {
            ReturnStatement returnStatement = ( ReturnStatement ) statement;
            if ( returnStatement.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
            {
                SimpleName simpleName = ( SimpleName ) returnStatement.getExpression();
                IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
                if ( varBinding.isField() )
                {
                    FieldAccess fieldAccess = ast.newFieldAccess();
                    fieldAccess.setExpression( ast.newThisExpression() );
                    fieldAccess.setName( ast.newSimpleName( simpleName.getIdentifier() ) );
                    returnStatement.setExpression( fieldAccess );
                }
            }
        } else
        {
            throw new RuntimeException( "Input not mapped yet" );
        }
    }

    public static void thisifyMethodInvocationArguments ( AST ast, MethodInvocation methodInvocation )
    {
        int size = methodInvocation.arguments().size();
        for ( int i = 0; i < size; methodInvocation.arguments() )
        {
            if ( methodInvocation.arguments().get( i ) instanceof SimpleName )
            {
                SimpleName simpleName = ( SimpleName ) methodInvocation.arguments().get( i );
                IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
                if ( varBinding.isField() )
                {
                    FieldAccess fieldAccess = ast.newFieldAccess();
                    fieldAccess.setExpression( ast.newThisExpression() );
                    fieldAccess.setName( ast.newSimpleName( simpleName.getIdentifier() ) );
                    methodInvocation.arguments().set( i, fieldAccess );
                }
            }
            i++;
        }
    }

    public static void thisifyInfixExpressionRightOperand ( AST ast, InfixExpression infixExpression, SimpleName simpleName )
    {
        FieldAccess fieldAccess = ast.newFieldAccess();
        fieldAccess.setExpression( ast.newThisExpression() );
        fieldAccess.setName( ast.newSimpleName( simpleName.getIdentifier() ) );
        infixExpression.setRightOperand( fieldAccess );
    }

}
