package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
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
        VariableDeclarationFragment variableDeclarationFragment = ( VariableDeclarationFragment )variableDeclarationStatement.fragments().get( 0 );
        new ExpressionVisitor( this.callbacks ).visit( variableDeclarationFragment.getInitializer() );

        return false;
    }
}
