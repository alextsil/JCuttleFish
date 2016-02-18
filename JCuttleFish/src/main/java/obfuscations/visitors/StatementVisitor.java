package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CastToAndVisit;

import java.util.Collection;


public class StatementVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;
    private final Logger logger = LoggerFactory.getLogger( StatementVisitor.class );

    public StatementVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    public boolean visit ( Statement statement )
    {
        this.callbacks.stream().forEach( c -> c.notify( statement ) );

        Expression expression;
        int statementNodeType = statement.getNodeType();

        if ( statementNodeType == ASTNode.EXPRESSION_STATEMENT )
        {
            expression = ( ( ExpressionStatement )statement ).getExpression();
            int expressionNodeType = expression.getNodeType();
            if ( expressionNodeType == ASTNode.METHOD_INVOCATION )
            {
                CastToAndVisit.methodInvocation( expression, this.callbacks );
            } else if ( expressionNodeType == ASTNode.ASSIGNMENT )
            {
                CastToAndVisit.assignment( expression, this.callbacks );
            } else if ( expressionNodeType == ASTNode.POSTFIX_EXPRESSION )
            {
                CastToAndVisit.postfixExpression( expression, this.callbacks );
            }
        } else if ( statementNodeType == ASTNode.RETURN_STATEMENT )
        {
            expression = ( ( ReturnStatement )statement ).getExpression();
            new ExpressionVisitor( this.callbacks ).visit( expression );
        } else if ( statementNodeType == ASTNode.VARIABLE_DECLARATION_STATEMENT )
        {
            CastToAndVisit.variableDeclarationStatement( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.IF_STATEMENT )
        {
            CastToAndVisit.ifStatement( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.BLOCK )
        {
            CastToAndVisit.block( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.ENHANCED_FOR_STATEMENT )
        {
            CastToAndVisit.enhancedForStatement( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.FOR_STATEMENT )
        {
            CastToAndVisit.forStatement( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.WHILE_STATEMENT )
        {
            CastToAndVisit.whileStatement( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.DO_STATEMENT )
        {
            CastToAndVisit.doWhileStatement( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.SWITCH_STATEMENT )
        {
            CastToAndVisit.switchStatement( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.SWITCH_CASE )
        {
            CastToAndVisit.switchCase( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.TRY_STATEMENT )
        {
            CastToAndVisit.tryStatement( statement, this.callbacks );
        } else if ( statementNodeType == ASTNode.CONSTRUCTOR_INVOCATION )
        {
            CastToAndVisit.constructorInvocation( statement, this.callbacks );
        } else
        {
            this.logger.warn( "Not mapped yet. type : " + statement.getClass() );
        }
        return true;
    }
}
