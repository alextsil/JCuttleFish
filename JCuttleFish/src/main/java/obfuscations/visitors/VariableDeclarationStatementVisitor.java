package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import java.util.Collection;


public class VariableDeclarationStatementVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public VariableDeclarationStatementVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( VariableDeclarationStatement variableDeclarationStatement )
    {
        this.callbacks.stream().forEach( c -> c.notify( variableDeclarationStatement ) );

        new TypeVisitor( this.callbacks ).visit( variableDeclarationStatement.getType() );

        VariableDeclarationFragment variableDeclarationFragment = ( VariableDeclarationFragment )variableDeclarationStatement.fragments().get( 0 );
        Expression expression = variableDeclarationFragment.getInitializer();
        if ( expression != null )
        {
            new ExpressionVisitor( this.callbacks ).visit( variableDeclarationFragment.getInitializer() );
        }

        return false;
    }
}
