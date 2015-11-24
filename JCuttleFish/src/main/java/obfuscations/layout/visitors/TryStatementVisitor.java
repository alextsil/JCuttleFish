package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.TryStatement;
import pojo.ObfuscationInfo;

import java.util.List;


public class TryStatementVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public TryStatementVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( TryStatement tryStatement )
    {
        new BlockVisitor( this.obfuscationInfo ).visit( tryStatement.getBody() );

        List<CatchClause> catchClauses = tryStatement.catchClauses();
        catchClauses.stream().forEach( c -> new BlockVisitor( this.obfuscationInfo ).visit( c.getBody() ) );

        if ( tryStatement.getFinally() != null )
        {
            new BlockVisitor( this.obfuscationInfo ).visit( tryStatement.getFinally() );
        }

        return false;
    }
}
