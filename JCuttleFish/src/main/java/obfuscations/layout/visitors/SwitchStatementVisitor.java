package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import pojo.ObfuscationInfo;

import java.util.List;


public class SwitchStatementVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public SwitchStatementVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( SwitchStatement node )
    {
        new ExpressionVisitor( this.obfuscationInfo ).preVisit2( node.getExpression() );

        List<Statement> statements = node.statements();
        statements.stream().forEach( s -> new StatementVisitor( this.obfuscationInfo ).visit( s ) );

        return false;
    }
}
