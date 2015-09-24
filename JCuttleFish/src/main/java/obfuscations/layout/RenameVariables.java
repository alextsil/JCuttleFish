package obfuscations.layout;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.SimpleName;

import java.util.List;


public class RenameVariables
{

    public static void renameMethodInvocationArguments ( List<? extends ASTNode> arguments, SimpleName originalName, String obfuscatedName )
    {
        for ( ASTNode argument : arguments )
        {
            if ( argument.getNodeType() == ASTNode.SIMPLE_NAME )
            {
                SimpleName simpleName = ( SimpleName ) argument;
                if ( simpleName.getIdentifier().equals( originalName.getIdentifier() ) )
                {
                    simpleName.setIdentifier( obfuscatedName );
                }
            }
            if ( argument.getNodeType() == ASTNode.FIELD_ACCESS )
            {
                FieldAccess fieldAccess = ( FieldAccess ) argument;
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
}
