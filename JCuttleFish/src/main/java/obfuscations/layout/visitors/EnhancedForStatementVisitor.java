package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;

import java.util.List;


public class EnhancedForStatementVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public EnhancedForStatementVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    public boolean visit ( EnhancedForStatement enhancedForStatement )
    {
        ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
        expressionVisitor.preVisit2( enhancedForStatement.getExpression() );

        if ( enhancedForStatement.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) enhancedForStatement.getExpression();
            if ( simpleName.getIdentifier().equals( this.obfuscatedVarName ) )
            {
                ModifyAst.thisifyName( this.ast, simpleName );
            }
        }

        List<Statement> statements = ( ( Block ) enhancedForStatement.getBody() ).statements();
        for ( Statement statement : statements )
        {
            StatementVisitor visitor = new StatementVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( statement );
        }
        return false;
    }
}
