package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Statement;

import java.util.Collection;
import java.util.List;


public class BlockVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public BlockVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( Block block )
    {
        this.callbacks.stream().forEach( c -> c.notify( block ) );

        List<Statement> statements = block.statements();
        statements.stream().forEach( s -> new StatementVisitor( this.callbacks ).visit( s ) );

        return false;
    }
}
