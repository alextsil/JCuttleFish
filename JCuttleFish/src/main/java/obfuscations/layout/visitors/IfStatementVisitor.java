package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import pojo.ObfuscationInfo;


public class IfStatementVisitor extends ASTVisitor
{

    private final ObfuscationInfo obfuscationInfo;

    public IfStatementVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( IfStatement ifStatement )
    {
        new ExpressionVisitor( this.obfuscationInfo ).visit( ifStatement.getExpression() );

        new StatementVisitor( this.obfuscationInfo ).visit( ifStatement.getThenStatement() );

        Statement elseStatement = ifStatement.getElseStatement();
        if ( elseStatement != null )
        {
            new StatementVisitor( this.obfuscationInfo ).visit( elseStatement );
        }
        return false;
    }
}
