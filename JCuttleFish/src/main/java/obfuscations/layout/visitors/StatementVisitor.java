package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;


public class StatementVisitor
{

    private ObfuscationInfo obfuscationInfo;

    private final Logger logger = LoggerFactory.getLogger( StatementVisitor.class );

    public StatementVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    public boolean visit ( Statement statement )
    {
        Expression expression;
        int statementNodeType = statement.getNodeType();

        if ( statementNodeType == ASTNode.EXPRESSION_STATEMENT )
        {
            expression = ( ( ExpressionStatement ) statement ).getExpression();
            int expressionNodeType = expression.getNodeType();
            if ( expressionNodeType == ASTNode.METHOD_INVOCATION )
            {
                CastToAndVisit.methodInvocation( expression, this.obfuscationInfo );
            }

            if ( expressionNodeType == ASTNode.ASSIGNMENT )
            {
                CastToAndVisit.assignment( expression, this.obfuscationInfo );
            }
        } else if ( statementNodeType == ASTNode.RETURN_STATEMENT )
        {
            expression = ( ( ReturnStatement ) statement ).getExpression();
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.obfuscationInfo );
            expressionVisitor.preVisit2( expression );
        } else if ( statementNodeType == ASTNode.VARIABLE_DECLARATION_STATEMENT )
        {
            CastToAndVisit.variableDeclarationStatement( statement, this.obfuscationInfo );
        } else if ( statementNodeType == ASTNode.IF_STATEMENT )
        {
            CastToAndVisit.ifStatement( statement, this.obfuscationInfo );
        } else if ( statementNodeType == ASTNode.ENHANCED_FOR_STATEMENT )
        {
            CastToAndVisit.enhancedForStatement( statement, this.obfuscationInfo );
        } else
        {
            logger.debug( "Not mapped yet" );
        }
        return false;
    }
}
