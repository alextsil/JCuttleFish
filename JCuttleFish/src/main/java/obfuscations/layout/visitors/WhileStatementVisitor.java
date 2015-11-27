package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
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
    public boolean visit ( WhileStatement node )
    {
        new ExpressionVisitor( this.callbacks ).visit( node.getExpression() );
        new StatementVisitor( this.callbacks ).visit( node.getBody() );

        return false;
    }
}
