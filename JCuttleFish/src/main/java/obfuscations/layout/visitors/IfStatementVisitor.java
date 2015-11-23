package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Statement;
import pojo.ObfuscationInfo;

import java.util.List;


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
        new ExpressionVisitor( this.obfuscationInfo ).preVisit2( ifStatement.getExpression() );

        //Visit ThenStatement
        Block thenStatementsBlock = ( Block )ifStatement.getThenStatement();
        List<Statement> statements = thenStatementsBlock.statements();
        statements.stream().forEach( s -> new StatementVisitor( this.obfuscationInfo ).visit( s ) );

        //Visit ElseStatement
        Statement elseStatement = ifStatement.getElseStatement();
        if ( elseStatement != null )
        {
            new StatementVisitor( this.obfuscationInfo ).visit( elseStatement );
        }
        return false;
    }
}
