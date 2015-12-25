package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;

import java.util.Collection;
import java.util.List;


public class SwitchStatementVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public SwitchStatementVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( SwitchStatement switchStatement )
    {
        this.callbacks.stream().forEach( c -> c.notify( switchStatement ) );

        new ExpressionVisitor( this.callbacks ).visit( switchStatement.getExpression() );

        List<Statement> statements = switchStatement.statements();
        statements.stream().forEach( s -> new StatementVisitor( this.callbacks ).visit( s ) );

        return false;
    }
}
