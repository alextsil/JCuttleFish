package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import util.OptionalUtils;

import java.util.Optional;


public class ExpressionVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public ExpressionVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    @Override
    //mono gia return_expression
    public boolean preVisit2 ( ASTNode expression )
    {
        if ( expression.getNodeType() == ASTNode.INFIX_EXPRESSION )
        {
            InfixExpression infixExpression = ( InfixExpression ) expression;
            InfixExpressionVisitor infixExpressionVisitor = new InfixExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            infixExpressionVisitor.visit( infixExpression );
        } else if ( expression.getNodeType() == ASTNode.FIELD_ACCESS )
        {
            FieldAccess fieldAccess = ( FieldAccess ) expression;
            FieldAccessVisitor fieldAccessVisitor = new FieldAccessVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            fieldAccessVisitor.visit( fieldAccess );
        } else if ( expression.getNodeType() == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName ) expression;
            SimpleNameVisitor simpleNameVisitor = new SimpleNameVisitor( this.originalVarSimpleName, this.obfuscatedVarName );
            simpleNameVisitor.visit( simpleName );
            Optional<IVariableBinding> optionalIvb = OptionalUtils.getIVariableBinding( simpleName );
            if ( simpleName.getIdentifier().equals( this.obfuscatedVarName )
                    && optionalIvb.map( i -> i.isField() ).orElse( false ) )
            {
                ModifyAst.renameSimpleName( simpleName, originalVarSimpleName, obfuscatedVarName );
                ModifyAst.thisifySimpleName( this.ast, simpleName );
            }
        } else if ( expression.getNodeType() == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression ) expression;
            ExpressionVisitor expressionVisitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            expressionVisitor.preVisit2( parenthesizedExpression.getExpression() );
        } else if ( expression.getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation ) expression;
            MethodInvocationVisitor visitor = new MethodInvocationVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
            visitor.visit( methodInvocation );
        } else if ( expression.getNodeType() == ASTNode.CLASS_INSTANCE_CREATION )
        {
            ClassInstanceCreation classInstanceCreation = ( ClassInstanceCreation ) expression;
            ModifyAst.renameMethodInvocationArguments( classInstanceCreation.arguments(), this.originalVarSimpleName, this.obfuscatedVarName );
        }
        return false;
    }
}
