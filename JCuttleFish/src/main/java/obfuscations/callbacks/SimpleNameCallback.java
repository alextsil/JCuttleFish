package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SimpleName;


public class SimpleNameCallback extends AstNodeFoundCallback
{

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof SimpleName )
        {
            super.addToCollection( v );
        }
    }
}
