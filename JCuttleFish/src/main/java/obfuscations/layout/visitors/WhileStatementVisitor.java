package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.WhileStatement;

import java.util.Collection;


public class WhileStatementVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public WhileStatementVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( WhileStatement whileStatement )
    {
        this.callbacks.stream().forEach( c -> c.notify( whileStatement ) );

        new ExpressionVisitor( this.callbacks ).visit( whileStatement.getExpression() );
        new StatementVisitor( this.callbacks ).visit( whileStatement.getBody() );

        return false;
    }
}
