package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
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
    public boolean visit ( SwitchStatement node )
    {
        new ExpressionVisitor( this.callbacks ).visit( node.getExpression() );

        List<Statement> statements = node.statements();
        statements.stream().forEach( s -> new StatementVisitor( this.callbacks ).visit( s ) );

        return false;
    }
}
