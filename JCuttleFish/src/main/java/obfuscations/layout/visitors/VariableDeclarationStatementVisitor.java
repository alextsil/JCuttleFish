package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import pojo.ObfuscationInfo;


public class VariableDeclarationStatementVisitor extends ASTVisitor
{

    private final ObfuscationInfo obfuscationInfo;

    public VariableDeclarationStatementVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( VariableDeclarationStatement variableDeclarationStatement )
    {
        VariableDeclarationFragment variableDeclarationFragment = ( VariableDeclarationFragment )variableDeclarationStatement.fragments().get( 0 );
        new ExpressionVisitor( this.obfuscationInfo ).visit( variableDeclarationFragment.getInitializer() );

        return false;
    }
}
