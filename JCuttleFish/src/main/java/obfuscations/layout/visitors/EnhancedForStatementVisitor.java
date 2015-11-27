package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.*;

import java.util.Collection;
import java.util.List;


public class EnhancedForStatementVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public EnhancedForStatementVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( EnhancedForStatement enhancedForStatement )
    {
        new ExpressionVisitor( this.callbacks ).visit( enhancedForStatement.getExpression() );

        if ( enhancedForStatement.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName )enhancedForStatement.getExpression();
        }

        List<Statement> statements = ( ( Block )enhancedForStatement.getBody() ).statements();
        statements.stream().forEach( s -> new StatementVisitor( this.callbacks ).visit( s ) );
        return false;
    }
}
