package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.*;

import java.util.Collection;
import java.util.List;


public class ForStatementVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public ForStatementVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( ForStatement forStatement )
    {
        this.callbacks.stream().forEach( c -> c.notify( forStatement ) );

        List initializers = forStatement.initializers();
        VariableDeclarationExpression vde = ( VariableDeclarationExpression )initializers.get( 0 );
        VariableDeclarationFragment variableDeclarationFragment = ( VariableDeclarationFragment )vde.fragments().get( 0 );
        Expression expression = variableDeclarationFragment.getInitializer();
        if ( expression != null )
        {
            new ExpressionVisitor( this.callbacks ).visit( variableDeclarationFragment.getInitializer() );
        }

        new ExpressionVisitor( this.callbacks ).visit( forStatement.getExpression() );

        if ( forStatement.getBody().getNodeType() == ASTNode.BLOCK )
        {
            List<Statement> statements = ( ( Block )forStatement.getBody() ).statements();
            statements.stream().forEach( s -> new StatementVisitor( this.callbacks ).visit( s ) );

        } else if ( forStatement.getBody().getNodeType() == ASTNode.FOR_STATEMENT )
        {
            ForStatement nestedForStatement = ( ForStatement )forStatement.getBody();
            new ForStatementVisitor( this.callbacks ).visit( nestedForStatement );
        } else if ( forStatement.getBody().getNodeType() == ASTNode.IF_STATEMENT )
        {
            IfStatement ifStatement = ( IfStatement )forStatement.getBody();
            new IfStatementVisitor( this.callbacks ).visit( ifStatement );
        }

        return false;
    }
}
