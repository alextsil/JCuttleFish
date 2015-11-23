package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;

import java.util.List;


public class MethodArgumentsVisitor
{

    private final ObfuscationInfo obfuscationInfo;
    private final Logger logger = LoggerFactory.getLogger( MethodArgumentsVisitor.class );

    public MethodArgumentsVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    public boolean visit ( List<Object> arguments )
    {
        for ( int i = 0; i < arguments.size(); i++ )
        {
            if ( arguments.get( i ) instanceof SimpleName )
            {
                SimpleName simpleName = ( SimpleName )arguments.get( i );
                //Check if obfuscated before thisifying
                if ( simpleName.getIdentifier().equals( this.obfuscationInfo.getObfuscatedVarName() ) )
                {
                    IVariableBinding varBinding = ( IVariableBinding )simpleName.resolveBinding();
                    if ( varBinding.isField() )
                    {
                        ModifyAst.thisifyName( this.obfuscationInfo.getAst(), simpleName );
                    }
                }
            } else if ( arguments.get( i ) instanceof QualifiedName )
            {
                QualifiedName qualifiedName = ( QualifiedName )arguments.get( i );
                //Check if obfuscated before thisifying
                if ( qualifiedName.getQualifier().getFullyQualifiedName().equals( this.obfuscationInfo.getObfuscatedVarName() ) )
                {
                    arguments.set( i, ModifyAst.convertNameToFieldAccess( this.obfuscationInfo.getAst(), qualifiedName ) );
                }
            } else if ( arguments.get( i ) instanceof InfixExpression )
            {
                CastToAndVisit.infixExpression( ( InfixExpression )arguments.get( i ), this.obfuscationInfo );
            } else if ( arguments.get( i ) instanceof PrefixExpression )
            {
                CastToAndVisit.prefixExpression( ( PrefixExpression )arguments.get( i ), this.obfuscationInfo );
            } else if ( arguments.get( i ) instanceof CastExpression )
            {
                CastExpression castExpression = ( CastExpression )arguments.get( i );
                new ExpressionVisitor( this.obfuscationInfo ).preVisit2( castExpression.getExpression() );
            } else if ( arguments.get( i ) instanceof MethodInvocation )
            {
                MethodInvocation methodInvocation = ( MethodInvocation )arguments.get( i );
                CastToAndVisit.methodInvocation( methodInvocation, this.obfuscationInfo );
            } else
            {
                this.logger.warn( "Not mapped yet" );
            }
        }
        return false;
    }
}
