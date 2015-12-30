package obfuscations.layoutobfuscation;

import org.eclipse.jdt.core.dom.*;


public class RenameNodeIdentifiers
{

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

    //Kanto recursive
    public static <T extends Type> void renameTypeIdentifiers ( T type, String obfuscatedName )
    {
        if ( type.isSimpleType() )
        {
            SimpleType simpleType = ( SimpleType )type;
            Name n = simpleType.getName();
            if ( n.isSimpleName() )
            {
                SimpleName sn = ( SimpleName )n;
                sn.setIdentifier( obfuscatedName );
            } else
            {
                throw new RuntimeException( "not mapped" );
            }
        } else if ( type.isParameterizedType() )
        {

        } else
        {
            throw new RuntimeException( "not mapped" );
        }
    }
}
