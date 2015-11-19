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
    public static void renameMethodInvocationArguments ( List<Object> arguments, SimpleName originalName, String obfuscatedName )
    {
        for ( Object argument : arguments )
        {
            if ( argument instanceof SimpleName )
            {
                SimpleName simpleName = ( SimpleName ) argument;
                Optional<IVariableBinding> optionalIvb = OptionalUtils.getIVariableBinding( simpleName );
                if ( simpleName.getIdentifier().equals( originalName.getIdentifier() )
                        && optionalIvb.map( i -> i.isField() ).orElse( false ) )
                {
                    simpleName.setIdentifier( obfuscatedName );
                }
            }
            if ( argument instanceof FieldAccess )
            {
                FieldAccess fieldAccess = ( FieldAccess ) argument;
                //Optional<IVariableBinding> optionalIvb = OptionalUtils.getIVariableBinding( fieldAccess );
                if ( fieldAccess.getName().getIdentifier().equals( originalName.getIdentifier() ) )
                {
                    fieldAccess.getName().setIdentifier( obfuscatedName );
                }
            }
            if ( argument instanceof QualifiedName )
            {
                QualifiedName qualifiedName = ( QualifiedName ) argument;
                SimpleName simpleName = ( SimpleName ) qualifiedName.getQualifier();
                Optional<IVariableBinding> optionalIvb = OptionalUtils.getIVariableBinding( simpleName );
                if ( simpleName.getIdentifier().equals( originalName.getIdentifier() )
                        && optionalIvb.map( i -> i.isField() ).orElse( false ) )
                {
                    simpleName.setIdentifier( obfuscatedName );
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

    /**
     * Thisifies the SimpleName by converting it to FieldAccess
     * and sets it back to its parent.
     * SimpleName can have any parent, and every parent requires different treatment. We treat all cases.
     */
    public static void thisifySimpleName ( AST ast, SimpleName simpleName )
    {
        //Thisify SimpleName by converting it to FieldAccess
        FieldAccess fieldAccess = ast.newFieldAccess();
        fieldAccess.setExpression( ast.newThisExpression() );
        fieldAccess.setName( ast.newSimpleName( simpleName.getIdentifier() ) );

        //This might occur if there's a method invocation a simple name ( text.get )
        //or if "text" is in the arguments list. So i check for both and replace accordingly.
        if ( simpleName.getParent().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) simpleName.getParent();
            if ( methodInvocation.getExpression() != null )
            {
                if ( methodInvocation.getExpression().equals( simpleName ) )
                {
                    methodInvocation.setExpression( fieldAccess );
                }
            }

            if ( methodInvocation.arguments().contains( simpleName ) )
            {
                methodInvocation.arguments().set( methodInvocation.arguments().indexOf( simpleName ), fieldAccess );
            }
        } else if ( simpleName.getParent().getNodeType() == ASTNode.ASSIGNMENT )
        {
            Assignment assignment = ( Assignment ) simpleName.getParent();
            if ( assignment.getLeftHandSide().equals( simpleName ) )
            {
                assignment.setLeftHandSide( fieldAccess );
            } else if ( assignment.getRightHandSide().equals( simpleName ) )
            {
                assignment.setRightHandSide( fieldAccess );
            }
        } else if ( simpleName.getParent().getNodeType() == ASTNode.RETURN_STATEMENT )
        {
            ReturnStatement returnStatement = ( ReturnStatement ) simpleName.getParent();
            returnStatement.setExpression( fieldAccess );
        } else if ( simpleName.getParent().getNodeType() == ASTNode.INFIX_EXPRESSION )
        {
            InfixExpression infixExpression = ( InfixExpression ) simpleName.getParent();
            if ( infixExpression.getLeftOperand().equals( simpleName ) )
            {
                infixExpression.setLeftOperand( fieldAccess );
            } else if ( infixExpression.getRightOperand().equals( simpleName ) )
            {
                infixExpression.setRightOperand( fieldAccess );
            } else if ( infixExpression.extendedOperands().contains( simpleName ) )
            {
                throw new RuntimeException( "time to fix this." );
            }
        } else if ( simpleName.getParent().getNodeType() == ASTNode.ENHANCED_FOR_STATEMENT )
        {
            EnhancedForStatement enhancedForStatement = ( EnhancedForStatement ) simpleName.getParent();
            enhancedForStatement.setExpression( fieldAccess );
        } else
        {
            int debugLine = 1;
        }
    }

    public static FieldAccess thisifyQualifiedName ( AST ast, QualifiedName qualifiedName )
    {
        SimpleName functionName = qualifiedName.getName();
        //Creating 2 FieldAccess nodes to represent ThisExpression and nested calls
        FieldAccess fieldAccess1 = ast.newFieldAccess();
        fieldAccess1.setName( ast.newSimpleName( functionName.getIdentifier() ) ); //Outer call

        FieldAccess fieldAccess2 = ast.newFieldAccess();
        fieldAccess2.setExpression( ast.newThisExpression() );
        fieldAccess2.setName( ast.newSimpleName( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier() ) );

        fieldAccess1.setExpression( fieldAccess2 );
        return fieldAccess1;
    }

}
