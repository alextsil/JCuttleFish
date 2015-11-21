package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import pojo.ObfuscationInfo;

import java.util.List;


public class EnhancedForStatementVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public EnhancedForStatementVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( EnhancedForStatement enhancedForStatement )
    {
        ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.obfuscationInfo );
        expressionVisitor.preVisit2( enhancedForStatement.getExpression() );

        if ( enhancedForStatement.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) enhancedForStatement.getExpression();
            if ( simpleName.getIdentifier().equals( this.obfuscationInfo.getObfuscatedVarName() ) )
            {
                ModifyAst.thisifyName( this.obfuscationInfo.getAst(), simpleName );
            }
        }

        List<Statement> statements = ( ( Block ) enhancedForStatement.getBody() ).statements();
        for ( Statement statement : statements )
        {
            StatementVisitor visitor = new StatementVisitor( this.obfuscationInfo );
            visitor.visit( statement );
        }
        return false;
    }
}
