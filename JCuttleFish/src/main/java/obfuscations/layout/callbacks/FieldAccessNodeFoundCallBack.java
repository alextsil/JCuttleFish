package obfuscations.layout.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.FieldAccess;


public class FieldAccessNodeFoundCallBack extends AstNodeFoundCallback
{

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof FieldAccess )
        {
            super.addToCollection( v );
        }
    }
}
