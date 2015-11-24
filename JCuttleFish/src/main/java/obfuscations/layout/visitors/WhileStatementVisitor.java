package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.WhileStatement;
import pojo.ObfuscationInfo;


public class WhileStatementVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public WhileStatementVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( WhileStatement node )
    {
        new ExpressionVisitor( this.obfuscationInfo ).preVisit2( node.getExpression() );
        new StatementVisitor( this.obfuscationInfo ).visit( node.getBody() );

        return false;
    }
}
