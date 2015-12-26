package util;

import org.eclipse.jdt.core.dom.*;


public class NodeIdentification
{

    public static String getNodeIdentifierString ( SimpleName simpleName )
    {
        return simpleName.getIdentifier();
    }

    public static String getNodeIdentifierString ( ClassInstanceCreation classInstanceCreation )
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

    public static String getNodeIdentifierString ( VariableDeclarationStatement variableDeclarationStatement )
    {
        VariableDeclarationFragment vdf = ( VariableDeclarationFragment )variableDeclarationStatement.fragments().get( 0 );
        return vdf.getName().getIdentifier();
    }

    public static String getNodeIdentifierString ( QualifiedName qualifiedName )
    {
        return qualifiedName.getName().getIdentifier();
    }

    public static String getNodeIdentifierString ( FieldAccess fieldAccess )
    {
        return fieldAccess.getName().getIdentifier();
    }

    public static String getNodeIdentifierString ( Type type )
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
            } else {
                throw new RuntimeException( "not mapped" );
            }
        } else
        {
            int debug = 1;
            throw new RuntimeException( "not mapped" );
        }
    }
}