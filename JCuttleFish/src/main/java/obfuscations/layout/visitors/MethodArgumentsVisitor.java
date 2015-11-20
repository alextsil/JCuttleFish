package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;

import java.util.List;


public class MethodArgumentsVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public MethodArgumentsVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    public boolean visit ( List<Object> arguments )
    {
        for ( int i = 0; i < arguments.size(); i++ )
        {
            if ( arguments.get( i ) instanceof SimpleName )
            {
                SimpleName simpleName = ( SimpleName ) arguments.get( i );
                //Check if obfuscated before thisifying
                if ( simpleName.getIdentifier().equals( obfuscatedVarName ) )
                {
                    IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
                    if ( varBinding.isField() )
                    {
                        ModifyAst.thisifyName( this.ast, simpleName );
                    }
                }
            } else if ( arguments.get( i ) instanceof QualifiedName )
            {
                QualifiedName qualifiedName = ( QualifiedName ) arguments.get( i );
                //Check if obfuscated before thisifying
                if ( qualifiedName.getQualifier().getFullyQualifiedName().equals( obfuscatedVarName ) )
                {
                    arguments.set( i, ModifyAst.convertNameToFieldAccess( ast, qualifiedName ) );
                }
            } else if ( arguments.get( i ) instanceof InfixExpression )
            {
                InfixExpression infixExpression = ( InfixExpression ) arguments.get( i );
                InfixExpressionVisitor visitor = new InfixExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( infixExpression );
            } else if ( arguments.get( i ) instanceof PrefixExpression )
            {
                PrefixExpression prefixExpression = ( PrefixExpression ) arguments.get( i );
                PrefixExpressionVisitor visitor = new PrefixExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.visit( prefixExpression );
            } else if ( arguments.get( i ) instanceof CastExpression )
            {
                CastExpression castExpression = ( CastExpression ) arguments.get( i );
                ExpressionVisitor visitor = new ExpressionVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                visitor.preVisit2( castExpression.getExpression() );
            } else if ( arguments.get( i ) instanceof MethodInvocation )
            {
                MethodInvocation methodInvocation = ( MethodInvocation ) arguments.get( i );
                MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor( this.originalVarSimpleName, this.obfuscatedVarName, this.ast );
                methodInvocationVisitor.visit( methodInvocation );
            } else
            {
                throw new RuntimeException( "Not mapped yet" );
            }
        }
        return false;
    }
}
