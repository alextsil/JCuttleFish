package obfuscations.layout;

import org.eclipse.jdt.core.dom.ASTNode;


public interface AstNodeFoundCallback
{

    public <V extends ASTNode> void addToCollection ( V v );
}
