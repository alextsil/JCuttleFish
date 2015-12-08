package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CastToAndVisit;

import java.util.Collection;
import java.util.List;


public class MethodArgumentsVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;
    private final Logger logger = LoggerFactory.getLogger( MethodArgumentsVisitor.class );

    public MethodArgumentsVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    public boolean visit ( List<Object> arguments )
    {
        for ( int i = 0; i < arguments.size(); i++ )
        {
            if ( arguments.get( i ) instanceof SimpleName )
            {
                SimpleName simpleName = ( SimpleName )arguments.get( i );
                new SimpleNameVisitor( this.callbacks ).visit( simpleName );
            } else if ( arguments.get( i ) instanceof FieldAccess )
            {
                FieldAccess fieldAccess = ( FieldAccess )arguments.get( i );
                new FieldAccessVisitor( this.callbacks ).visit( fieldAccess );
            } else if ( arguments.get( i ) instanceof QualifiedName )
            {
                QualifiedName qualifiedName = ( QualifiedName )arguments.get( i );
                new QualifiedNameVisitor( this.callbacks ).visit( qualifiedName );
            } else if ( arguments.get( i ) instanceof InfixExpression )
            {
                CastToAndVisit.infixExpression( ( InfixExpression )arguments.get( i ), this.callbacks );
            } else if ( arguments.get( i ) instanceof PrefixExpression )
            {
                CastToAndVisit.prefixExpression( ( PrefixExpression )arguments.get( i ), this.callbacks );
            } else if ( arguments.get( i ) instanceof CastExpression )
            {
                CastExpression castExpression = ( CastExpression )arguments.get( i );
                new ExpressionVisitor( this.callbacks ).visit( castExpression.getExpression() );
            } else if ( arguments.get( i ) instanceof MethodInvocation )
            {
                MethodInvocation methodInvocation = ( MethodInvocation )arguments.get( i );
                CastToAndVisit.methodInvocation( methodInvocation, this.callbacks );
            } else
            {
                this.logger.warn( "Not mapped yet. Type :  " + arguments.get( i ).getClass() );
            }
        }
        return false;
    }
}
