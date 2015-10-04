package util;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;

import java.util.Optional;


public class OptionalUtils
{

    public static Optional<IVariableBinding> getIVariableBinding ( ASTNode node )
    {
        if ( node.getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName sn = ( SimpleName ) node;
            IVariableBinding ivb = ( IVariableBinding ) sn.resolveBinding();
            if ( ivb != null )
            {
                return Optional.of( ivb );
            }
        }
        if ( node.getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fa = ( FieldAccess ) node;
            IVariableBinding ivb = fa.resolveFieldBinding();
            if ( ivb != null )
            {
                return Optional.of( ivb );
            }
        }
        //Default
        return Optional.empty();
    }
}
