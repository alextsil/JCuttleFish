package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
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
    public boolean visit ( DoStatement node )
    {
        new ExpressionVisitor( this.callbacks ).visit( node.getExpression() );
        new StatementVisitor( this.callbacks ).visit( node.getBody() );

        return false;
    }
}
