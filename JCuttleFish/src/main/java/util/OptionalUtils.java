package util;

import org.eclipse.jdt.core.dom.*;

import java.util.Optional;


public class OptionalUtils
{

    public static Optional<IVariableBinding> getIVariableBinding ( ASTNode node )
    {
        if ( node.getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName sn = ( SimpleName )node;
            IVariableBinding ivb = ( IVariableBinding )sn.resolveBinding();
            if ( ivb != null )
            {
                return Optional.of( ivb );
            }
        }
        if ( node.getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fa = ( FieldAccess )node;
            IVariableBinding ivb = fa.resolveFieldBinding();
            if ( ivb != null )
            {
                return Optional.of( ivb );
            }
        }
        if ( node.getNodeType() == ASTNode.VARIABLE_DECLARATION_FRAGMENT )
        {
            VariableDeclarationFragment vdf = ( VariableDeclarationFragment )node;
            IVariableBinding ivb = vdf.resolveBinding();
            if ( ivb != null )
            {
                return Optional.of( ivb );
            }
        }
        if ( node.getNodeType() == ASTNode.SINGLE_VARIABLE_DECLARATION )
        {
            SingleVariableDeclaration svd = ( SingleVariableDeclaration )node;
            IVariableBinding ivb = svd.resolveBinding();
            if ( ivb != null )
            {
                return Optional.of( ivb );
            }
        }
        //Default
        return Optional.empty();
    }
}
