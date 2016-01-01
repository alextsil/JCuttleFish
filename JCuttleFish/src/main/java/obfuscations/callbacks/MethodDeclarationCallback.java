package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;


public class MethodDeclarationCallback extends AstNodeFoundCallback
{

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof MethodDeclaration )
        {
            super.addToCollection( v );
        }
    }
}
