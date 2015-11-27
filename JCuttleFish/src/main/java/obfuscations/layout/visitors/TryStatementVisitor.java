package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.TryStatement;

import java.util.Collection;
import java.util.List;


public class TryStatementVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public TryStatementVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( TryStatement tryStatement )
    {
        new BlockVisitor( this.callbacks ).visit( tryStatement.getBody() );

        List<CatchClause> catchClauses = tryStatement.catchClauses();
        catchClauses.stream().forEach( c -> new BlockVisitor( this.callbacks ).visit( c.getBody() ) );

        if ( tryStatement.getFinally() != null )
        {
            new BlockVisitor( this.callbacks ).visit( tryStatement.getFinally() );
        }

        return false;
    }
}
