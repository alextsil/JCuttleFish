package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;

import java.util.Collection;
import java.util.List;


public class MethodDeclarationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public MethodDeclarationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( MethodDeclaration methodDeclaration )
    {
        this.callbacks.stream().forEach( c -> c.notify( methodDeclaration ) );

        List<Statement> statements = methodDeclaration.getBody().statements();
        statements.stream().forEach( s -> new StatementVisitor( this.callbacks ).visit( s ) );

        return false;
    }
}
