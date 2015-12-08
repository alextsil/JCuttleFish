package obfuscations.layout.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;


public class VariableDeclarationStatementNodeFoundCallBack extends AstNodeFoundCallback
{

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof VariableDeclarationStatement )
        {
            super.addToCollection( v );
        }
    }
}
