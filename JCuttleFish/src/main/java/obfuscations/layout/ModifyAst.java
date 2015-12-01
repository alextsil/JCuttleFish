package obfuscations.layout;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.*;
import util.CastToAndVisit;

import java.util.Collection;
import java.util.List;


public class ModifyAst
{

    /**
     * Compares the given (method's) arguments to the given SimpleName and replaces the match found with obfuscatedName parameter.
     *
     * @param arguments The list of arguments to search into
     * @param callbacks
     */
    public static void renameMethodInvocationArguments ( List<Object> arguments, Collection<AstNodeFoundCallback> callbacks )
    {
        for ( Object argument : arguments )
        {
            if ( argument instanceof SimpleName )
            {
                SimpleName simpleName = ( SimpleName )argument;
                CastToAndVisit.simpleName( simpleName, callbacks );
            } else if ( argument instanceof FieldAccess )
            {
                FieldAccess fieldAccess = ( FieldAccess )argument;
                CastToAndVisit.fieldAccess( fieldAccess, callbacks );
            } else if ( argument instanceof QualifiedName )
            {
                QualifiedName qualifiedName = ( QualifiedName )argument;
                CastToAndVisit.qualifiedName( qualifiedName, callbacks );
            } else if ( argument instanceof InfixExpression )
            {
                InfixExpression infixExpression = ( InfixExpression )argument;
                CastToAndVisit.infixExpression( infixExpression, callbacks );
            }
        }
    }

    public static void renameFieldAccessName ( FieldAccess fieldAccess, String obfuscatedVarName )
    {
        if ( fieldAccess.getExpression().getNodeType() == ASTNode.METHOD_INVOCATION )
        {
            MethodInvocation methodInvocation = ( MethodInvocation )fieldAccess.getExpression();
            if ( methodInvocation.getExpression().getNodeType() == ASTNode.SIMPLE_NAME )
            {
                SimpleName simpleName = ( SimpleName )methodInvocation.getExpression();
                renameSimpleName( simpleName, obfuscatedVarName );
            }
        } else
        {
            fieldAccess.getName().setIdentifier( obfuscatedVarName );
        }
    }

    public static void renameSimpleName ( SimpleName simpleName, String obfuscatedVarName )
    {
        simpleName.setIdentifier( obfuscatedVarName );
    }
}
