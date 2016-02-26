package util;

import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Deque;
import java.util.Optional;
import java.util.function.BiConsumer;


public class RenameNodeUtil
{

    private static final Logger logger = LoggerFactory.getLogger( RenameNodeUtil.class );
    public static BiConsumer<ASTNode, Deque<String>> renameFieldASTNode = ( node, obfuscatedNames ) ->
    {
        if ( node instanceof SimpleName )
        {
            SimpleName simpleName = ( SimpleName )node;
            Optional<IVariableBinding> ivb = OptionalUtils.getIVariableBinding( simpleName );
            if ( ivb.isPresent() )
            {
                if ( ivb.get().isField() )
                {
                    RenameNodeUtil.renameSimpleName( simpleName, obfuscatedNames.peekFirst() );
                }
            }
        } else if ( node instanceof FieldAccess )
        {
            FieldAccess fieldAccess = ( FieldAccess )node;
            RenameNodeUtil.renameFieldAccessName( fieldAccess, obfuscatedNames.peekFirst() );
        } else if ( node instanceof FieldDeclaration )
        {
            FieldDeclaration fieldDeclaration = ( FieldDeclaration )node;
            RenameNodeUtil.renameFieldDeclaration( fieldDeclaration, obfuscatedNames.peekFirst() );
        }
    };


    public static void renameFieldAccessName ( FieldAccess fieldAccess, String obfuscatedVarName )
    {
        if ( fieldAccess.getExpression().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation )fieldAccess.getExpression();
            if ( methodInvocation.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
            {
                SimpleName simpleName = ( SimpleName )methodInvocation.getExpression();
                renameSimpleName( simpleName, obfuscatedVarName );
            }
        } else
        {
            fieldAccess.getName().setIdentifier( obfuscatedVarName );
        }
    }

    public static void renameSimpleName ( SimpleName simpleName, String obfuscatedVarName )
    {
        simpleName.setIdentifier( obfuscatedVarName );
    }

    public static void renameFieldDeclaration ( FieldDeclaration fieldDeclaration, String obfuscatedVarName )
    {
        VariableDeclarationFragment vdf = ( VariableDeclarationFragment )fieldDeclaration.fragments().get( 0 );
        vdf.getName().setIdentifier( obfuscatedVarName );
    }

    public static <T extends Type> void renameIdentifierOccurencesInTypeHierarchy ( T type, String targetIdentifier, String obfuscatedName )
    {
        if ( type.isSimpleType() )
        {
            SimpleType simpleType = ( SimpleType )type;
            Name n = simpleType.getName();
            if ( n.isSimpleName() )
            {
                SimpleName sn = ( SimpleName )n;
                if ( sn.getIdentifier().equals( targetIdentifier ) )
                {
                    sn.setIdentifier( obfuscatedName );
                }
            } else
            {
                logger.warn( "QualifiedName found in type. Cannot handle - Might cause test errors." ); //FIXME: Not sure how to handle
            }
        } else if ( type.isParameterizedType() )
        {
            ParameterizedType parameterizedType = ( ParameterizedType )type;
            renameIdentifierOccurencesInTypeHierarchy( parameterizedType.getType(), targetIdentifier, obfuscatedName );
            parameterizedType.typeArguments().stream()
                    .forEach( ta -> renameIdentifierOccurencesInTypeHierarchy( ( T )ta, targetIdentifier, obfuscatedName ) );
        } else if ( type.isArrayType() )
        {
            ArrayType arrayType = ( ArrayType )type;
            renameIdentifierOccurencesInTypeHierarchy( arrayType.getElementType(), targetIdentifier, obfuscatedName );
        } else if ( type.isWildcardType() )
        {
            WildcardType wildcardType = ( WildcardType )type;
            renameIdentifierOccurencesInTypeHierarchy( wildcardType.getBound(), targetIdentifier, obfuscatedName );
        } else if ( type.isPrimitiveType() )
        {
            //do nothing
        } else
        {
            throw new RuntimeException( type.getClass() + " not mapped" );
        }
    }

    public static void renameMethodDeclaration ( MethodDeclaration methodDeclaration, String obfuscatedName )
    {
        methodDeclaration.getName().setIdentifier( obfuscatedName );
    }
}
