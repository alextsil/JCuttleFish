package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;
import util.OptionalUtils;

import java.util.Optional;


public class ExpressionVisitor extends ASTVisitor
{

    private final ObfuscationInfo obfuscationInfo;

    public ExpressionVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean preVisit2 ( ASTNode expression )
    {
        int expressionNodeType = expression.getNodeType();
        if ( expressionNodeType == ASTNode.INFIX_EXPRESSION )
        {
            CastToAndVisit.infixExpression( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.FIELD_ACCESS )
        {
            CastToAndVisit.fieldAccess( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.QUALIFIED_NAME )
        {
            CastToAndVisit.qualifiedName( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.SIMPLE_NAME )
        {
            SimpleName simpleName = ( SimpleName )expression;
            new SimpleNameVisitor( this.obfuscationInfo.getOriginalVarSimpleName(), this.obfuscationInfo.getObfuscatedVarName() )
                    .visit( simpleName );
            Optional<IVariableBinding> optionalIvb = OptionalUtils.getIVariableBinding( simpleName );
            if ( simpleName.getIdentifier().equals( this.obfuscationInfo.getObfuscatedVarName() )
                    && optionalIvb.map( IVariableBinding::isField ).orElse( false ) )
            {
                ModifyAst.renameSimpleName( simpleName, this.obfuscationInfo.getOriginalVarSimpleName(),
                        this.obfuscationInfo.getObfuscatedVarName() );
                ModifyAst.thisifyName( this.obfuscationInfo.getAst(), simpleName );
            }
        } else if ( expressionNodeType == ASTNode.PARENTHESIZED_EXPRESSION )
        {
            ParenthesizedExpression parenthesizedExpression = ( ParenthesizedExpression )expression;
            new ExpressionVisitor( this.obfuscationInfo ).preVisit2( parenthesizedExpression.getExpression() );
        } else if ( expressionNodeType == ASTNode.METHOD_INVOCATION )
        {
            CastToAndVisit.methodInvocation( expression, this.obfuscationInfo );
        } else if ( expressionNodeType == ASTNode.CLASS_INSTANCE_CREATION )
        {
            CastToAndVisit.classInstanceCreation( expression, this.obfuscationInfo );
        }
        return false;
    }
}
