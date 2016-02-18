package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;


public class EnumCallback extends AstNodeFoundCallback
{

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof EnumDeclaration || v instanceof EnumConstantDeclaration )
        {
            super.addToCollection( v );
        }
    }
}
