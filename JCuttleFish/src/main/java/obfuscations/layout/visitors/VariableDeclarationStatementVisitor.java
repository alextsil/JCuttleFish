package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;


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
        int variableDeclarationFragmentInitializerNodeType = variableDeclarationFragment.getInitializer().getNodeType();

        if ( variableDeclarationFragmentInitializerNodeType == ASTNode.QUALIFIED_NAME )
        {
            CastToAndVisit.qualifiedName( variableDeclarationFragment.getInitializer(), this.obfuscationInfo );

        } else if ( variableDeclarationFragmentInitializerNodeType == ASTNode.METHOD_INVOCATION )
        {
            CastToAndVisit.methodInvocation( variableDeclarationFragment.getInitializer(), this.obfuscationInfo );
        }
        return false;
    }
}
