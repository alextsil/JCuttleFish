package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;

import java.util.Collection;


public class IfStatementVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public IfStatementVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( IfStatement ifStatement )
    {
        this.callbacks.stream().forEach( c -> c.notify( ifStatement ) );

        new ExpressionVisitor( this.callbacks ).visit( ifStatement.getExpression() );
        new StatementVisitor( this.callbacks ).visit( ifStatement.getThenStatement() );

        Statement elseStatement = ifStatement.getElseStatement();
        if ( elseStatement != null )
        {
            new StatementVisitor( this.callbacks ).visit( elseStatement );
        }
        return false;
    }
}
