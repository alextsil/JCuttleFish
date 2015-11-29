package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.DoStatement;

import java.util.Collection;


public class DoStatementVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public DoStatementVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( DoStatement doStatement )
    {
        this.callbacks.stream().forEach( c -> c.notify( doStatement ) );

        new ExpressionVisitor( this.callbacks ).visit( doStatement.getExpression() );
        new StatementVisitor( this.callbacks ).visit( doStatement.getBody() );

        return false;
    }
}
