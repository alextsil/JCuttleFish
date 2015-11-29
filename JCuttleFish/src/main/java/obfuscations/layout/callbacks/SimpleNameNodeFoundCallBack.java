package obfuscations.layout.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.SimpleName;


public class SimpleNameNodeFoundCallBack extends AstNodeFoundCallback
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
