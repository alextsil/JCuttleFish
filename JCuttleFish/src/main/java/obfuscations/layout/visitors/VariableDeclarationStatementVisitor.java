package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;


public class VariableDeclarationStatementVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public VariableDeclarationStatementVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( VariableDeclarationStatement variableDeclarationStatement )
    {
        VariableDeclarationFragment variableDeclarationFragment = ( VariableDeclarationFragment ) variableDeclarationStatement.fragments().get( 0 );
        int variableDeclarationFragmentInitializerNodeType = variableDeclarationFragment.getInitializer().getNodeType();

        if ( variableDeclarationFragmentInitializerNodeType == ASTNode.QUALIFIED_NAME )
        {
            QualifiedName qualifiedName = ( QualifiedName ) variableDeclarationFragment.getInitializer();
            QualifiedNameVisitor visitor = new QualifiedNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( qualifiedName );

            if ( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier().equals( obfuscatedVarName ) )
            {
                ModifyAst.thisifyName( this.ast, qualifiedName );
            }
        } else if ( variableDeclarationFragmentInitializerNodeType == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) variableDeclarationFragment.getInitializer();
            MethodInvocationVisitor visitor = new MethodInvocationVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( methodInvocation );
        }
        return false;
    }
}
