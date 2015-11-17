package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;

import java.util.List;


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
    public boolean visit ( VariableDeclarationStatement node )
    {
        VariableDeclarationFragment vdf = ( VariableDeclarationFragment ) node.fragments().get( 0 );
        if ( vdf.getInitializer().getNodeType() == ASTNode.QUALIFIED_NAME )
        {
            QualifiedName qualifiedName = ( QualifiedName ) vdf.getInitializer();
            QualifiedNameVisitor visitor = new QualifiedNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( qualifiedName );

            if ( ( ( SimpleName ) qualifiedName.getQualifier() ).getIdentifier().equals( obfuscatedVarName ) )
            {
                vdf.setInitializer( ModifyAst.thisifyQualifiedName( this.ast, qualifiedName ) );
            }
        } else if ( vdf.getInitializer().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) vdf.getInitializer();
            MethodInvocationExpressionVisitor visitor = new MethodInvocationExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( methodInvocation );

            //Rename arguments
            List<Object> arguments = methodInvocation.arguments();
            ModifyAst.renameMethodInvocationArguments( arguments, originalVarSimpleName, obfuscatedVarName );
            MethodArgumentsVisitor visitor2 = new MethodArgumentsVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor2.visit( arguments );
        }
        return false;
    }
}
