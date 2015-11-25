package obfuscations.layout;

import org.eclipse.jdt.core.dom.*;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;

import java.util.List;


public class ModifyAst
{

    /**
     * Compares the given (method's) arguments to the given SimpleName and replaces the match found with obfuscatedName parameter.
     *
     * @param arguments       The list of arguments to search into
     * @param obfuscationInfo
     */
    public static void renameMethodInvocationArguments ( List<Object> arguments, ObfuscationInfo obfuscationInfo )
    {
        for ( Object argument : arguments )
        {
            if ( argument instanceof SimpleName )
            {
                SimpleName simpleName = ( SimpleName )argument;
                CastToAndVisit.simpleName( simpleName, obfuscationInfo );
            } else if ( argument instanceof FieldAccess )
            {
                FieldAccess fieldAccess = ( FieldAccess )argument;
                CastToAndVisit.fieldAccess( fieldAccess, obfuscationInfo );
            } else if ( argument instanceof QualifiedName )
            {
                QualifiedName qualifiedName = ( QualifiedName )argument;
                CastToAndVisit.qualifiedName( qualifiedName, obfuscationInfo );
            } else if ( argument instanceof InfixExpression )
            {
                InfixExpression infixExpression = ( InfixExpression )argument;
                CastToAndVisit.infixExpression( infixExpression, obfuscationInfo );
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

    public static void thisifyName ( AST ast, Name name )
    {
        FieldAccess generatedFieldAccess = ModifyAst.convertNameToFieldAccess( ast, name );
        int nameParentNodeType = name.getParent().getNodeType();

        //This might occur if there's a method invocation on a simple name ( text.get )
        //or if "text" is in the arguments list. So i check for both and replace accordingly.
        if ( nameParentNodeType == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation )name.getParent();
            if ( methodInvocation.getExpression() != null )
            {
                if ( methodInvocation.getExpression().equals( name ) )
                {
                    methodInvocation.setExpression( generatedFieldAccess );
                }
            }
            if ( methodInvocation.arguments().contains( name ) )
            {
                methodInvocation.arguments().set( methodInvocation.arguments().indexOf( name ), generatedFieldAccess );
            }
        } else if ( nameParentNodeType == ASTNode.CLASS_INSTANCE_CREATION )
        {
            ClassInstanceCreation classInstanceCreation = ( ClassInstanceCreation )name.getParent();
            if ( classInstanceCreation.arguments().contains( name ) )
            {
                classInstanceCreation.arguments().set( classInstanceCreation.arguments().indexOf( name ), generatedFieldAccess );
            }
        } else if ( nameParentNodeType == ASTNode.ASSIGNMENT )
        {
            Assignment assignment = ( Assignment )name.getParent();
            if ( assignment.getLeftHandSide().equals( name ) )
            {
                assignment.setLeftHandSide( generatedFieldAccess );
            } else if ( assignment.getRightHandSide().equals( name ) )
            {
                assignment.setRightHandSide( generatedFieldAccess );
            }
        } else if ( nameParentNodeType == ASTNode.RETURN_STATEMENT )
        {
            ReturnStatement returnStatement = ( ReturnStatement )name.getParent();
            returnStatement.setExpression( generatedFieldAccess );
        } else if ( nameParentNodeType == ASTNode.INFIX_EXPRESSION )
        {
            InfixExpression infixExpression = ( InfixExpression )name.getParent();
            if ( infixExpression.getLeftOperand().equals( name ) )
            {
                infixExpression.setLeftOperand( generatedFieldAccess );
            } else if ( infixExpression.getRightOperand().equals( name ) )
            {
                infixExpression.setRightOperand( generatedFieldAccess );
            } else if ( infixExpression.extendedOperands().contains( name ) )
            {
                infixExpression.extendedOperands().set( infixExpression.extendedOperands().indexOf( name ), generatedFieldAccess );
            }
        } else if ( nameParentNodeType == ASTNode.PREFIX_EXPRESSION )
        {
            PrefixExpression prefixExpression = ( PrefixExpression )name.getParent();
            prefixExpression.setOperand( generatedFieldAccess );
        } else if ( nameParentNodeType == ASTNode.POSTFIX_EXPRESSION )
        {
            PostfixExpression postfixExpression = ( PostfixExpression )name.getParent();
            postfixExpression.setOperand( generatedFieldAccess );
        } else if ( nameParentNodeType == ASTNode.ENHANCED_FOR_STATEMENT )
        {
            EnhancedForStatement enhancedForStatement = ( EnhancedForStatement )name.getParent();
            enhancedForStatement.setExpression( generatedFieldAccess );
        } else if ( nameParentNodeType == ASTNode.VARIABLE_DECLARATION_FRAGMENT )
        {
            VariableDeclarationFragment variableDeclarationFragment = ( VariableDeclarationFragment )name.getParent();
            variableDeclarationFragment.setInitializer( generatedFieldAccess );
        } else if ( nameParentNodeType == ASTNode.SWITCH_STATEMENT )
        {
            SwitchStatement switchStatement = ( SwitchStatement )name.getParent();
            switchStatement.setExpression( generatedFieldAccess );
        } else if ( nameParentNodeType == ASTNode.ARRAY_ACCESS )
        {
            ArrayAccess arrayAccess = ( ArrayAccess )name.getParent();
            arrayAccess.setArray( generatedFieldAccess );
        } else
        {
            throw new RuntimeException( "Not mapped yet" );
        }
    }

    public static FieldAccess convertNameToFieldAccess ( AST ast, Name name )
    {
        FieldAccess generatedFieldAccess = ast.newFieldAccess();
        if ( name.getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName )name;
            generatedFieldAccess.setExpression( ast.newThisExpression() );
            generatedFieldAccess.setName( ast.newSimpleName( simpleName.getIdentifier() ) );

        } else if ( name.getNodeType() == ASTNode.QUALIFIED_NAME )
        {
            QualifiedName qualifiedName = ( QualifiedName )name;
            SimpleName functionName = qualifiedName.getName();
            //Creating 2 FieldAccess nodes to represent ThisExpression and nested calls
            generatedFieldAccess.setName( ast.newSimpleName( functionName.getIdentifier() ) ); //Outer call

            FieldAccess fieldAccess2 = ast.newFieldAccess();
            fieldAccess2.setExpression( ast.newThisExpression() );
            fieldAccess2.setName( ast.newSimpleName( ( ( SimpleName )qualifiedName.getQualifier() ).getIdentifier() ) );

            generatedFieldAccess.setExpression( fieldAccess2 );
        }
        return generatedFieldAccess;
    }

}
