package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.*;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;


public class IfStatementVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public IfStatementVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( IfStatement ifStatement )
    {
        if ( ifStatement.getExpression().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            CastToAndVisit.methodInvocation( ifStatement.getExpression(), this.obfuscationInfo );
        }

        //Visit ThenStatement
        Block thenStatements = ( Block ) ifStatement.getThenStatement();
        for ( Object statement : thenStatements.statements() )
        {
            StatementVisitor visitor = new StatementVisitor( this.obfuscationInfo );
            visitor.visit( ( Statement ) statement );
        }

        //Visit ElseStatement
        Block elseStatements = ( Block ) ifStatement.getElseStatement();
        if ( elseStatements != null )
        {
            for ( Object statement : elseStatements.statements() )
            {
                StatementVisitor visitor = new StatementVisitor( this.obfuscationInfo );
                visitor.visit( ( Statement ) statement );
            }
        }
        return false;
    }
}
