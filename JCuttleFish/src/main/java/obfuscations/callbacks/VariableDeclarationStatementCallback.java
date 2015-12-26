package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;


public class VariableDeclarationStatementCallback extends AstNodeFoundCallback
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
