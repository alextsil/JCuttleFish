package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.QualifiedName;


public class QualifiedNameCallback extends AstNodeFoundCallback
{

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof QualifiedName )
        {
            super.addToCollection( v );
        }
    }
}
