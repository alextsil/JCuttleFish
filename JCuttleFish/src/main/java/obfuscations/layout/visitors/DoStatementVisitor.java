package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.DoStatement;
import pojo.ObfuscationInfo;


public class DoStatementVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public DoStatementVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( DoStatement node )
    {
        new ExpressionVisitor( this.obfuscationInfo ).preVisit2( node.getExpression() );
        new StatementVisitor( this.obfuscationInfo ).visit( node.getBody() );

        return false;
    }
}
