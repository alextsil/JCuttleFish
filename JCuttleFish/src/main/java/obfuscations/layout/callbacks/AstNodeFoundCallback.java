package obfuscations.layout.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;

import java.util.Collection;
import java.util.HashSet;


public abstract class AstNodeFoundCallback
{

    private Collection<ASTNode> foundNodes = new HashSet<>();

    protected <V extends ASTNode> void addToCollection ( V v )
    {
        this.foundNodes.add( v );
    }

    public Collection<ASTNode> getFoundNodes ()
    {
        return this.foundNodes;
    }

    public abstract <V extends ASTNode> void notify ( V v );
}
