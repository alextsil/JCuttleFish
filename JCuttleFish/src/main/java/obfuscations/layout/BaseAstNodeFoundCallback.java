package obfuscations.layout;

import org.eclipse.jdt.core.dom.ASTNode;

import java.util.Collection;
import java.util.HashSet;


public class BaseAstNodeFoundCallback implements AstNodeFoundCallback
{

    private Collection<ASTNode> foundNodes = new HashSet<>();

    @Override
    public <V extends ASTNode> void addToCollection ( V v )
    {
        this.foundNodes.add( v );
    }
}
