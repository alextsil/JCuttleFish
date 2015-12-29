package util;

import org.eclipse.jdt.core.dom.*;


public class NodeIdentification
{

    public static String mapNodeToIdentifier ( ASTNode node )
    {
        if ( node instanceof SimpleName )
            return getNodeIdentifierString( ( SimpleName )node );
        if ( node instanceof ClassInstanceCreation )
            return getNodeIdentifierString( ( ClassInstanceCreation )node );
        if ( node instanceof VariableDeclarationStatement )
            return getNodeIdentifierString( ( VariableDeclarationStatement )node );
        if ( node instanceof QualifiedName )
            return getNodeIdentifierString( ( QualifiedName )node );
        if ( node instanceof FieldAccess )
            return getNodeIdentifierString( ( FieldAccess )node );
        if ( node instanceof Type )
            return getNodeIdentifierString( ( Type )node );
        if ( node instanceof FieldDeclaration )
            return getNodeIdentifierString( ( FieldDeclaration )node );
        if ( node instanceof TypeDeclaration )
            return getNodeIdentifierString( ( TypeDeclaration )node );

        throw new IllegalArgumentException( "Node type not supported" );
    }

    private static String getNodeIdentifierString ( FieldDeclaration fieldDeclaration )
    {
        VariableDeclarationFragment vdf = ( VariableDeclarationFragment )fieldDeclaration.fragments().get( 0 );
        return vdf.getName().getIdentifier();
    }

    private static String getNodeIdentifierString ( TypeDeclaration typeDeclaration )
    {
        return typeDeclaration.getName().getIdentifier();
    }

    private static String getNodeIdentifierString ( SimpleName simpleName )
    {
        return simpleName.getIdentifier();
    }

    private static String getNodeIdentifierString ( ClassInstanceCreation classInstanceCreation )
    {
        Type type = classInstanceCreation.getType();
        if ( type.isSimpleType() )
        {
            return getNodeIdentifierString( type );
        } else if ( type.isParameterizedType() )
        {
            ParameterizedType parameterizedType = ( ParameterizedType )type;
            Type nestedType = parameterizedType.getType();
            return getNodeIdentifierString( nestedType );
        } else
        {
            throw new RuntimeException( "not mapped" );
        }
    }

    private static String getNodeIdentifierString ( VariableDeclarationStatement variableDeclarationStatement )
    {
        VariableDeclarationFragment vdf = ( VariableDeclarationFragment )variableDeclarationStatement.fragments().get( 0 );
        return vdf.getName().getIdentifier();
    }

    private static String getNodeIdentifierString ( QualifiedName qualifiedName )
    {
        return qualifiedName.getName().getIdentifier();
    }

    private static String getNodeIdentifierString ( FieldAccess fieldAccess )
    {
        return fieldAccess.getName().getIdentifier();
    }

    private static String getNodeIdentifierString ( Type type )
    {
        SimpleType simpleType = ( SimpleType )type;
        Name name = simpleType.getName();
        if ( name.isSimpleName() )
        {
            SimpleName simpleName = ( SimpleName )name;
            return simpleName.getIdentifier();
        } else if ( name.isQualifiedName() )
        {
            QualifiedName qualifiedName = ( QualifiedName )name;
            if ( qualifiedName.getName().isSimpleName() )
            {
                return qualifiedName.getName().getIdentifier();
            } else
            {
                throw new RuntimeException( "not mapped" );
            }
        } else
        {
            throw new RuntimeException( "not mapped" );
        }
    }

}
