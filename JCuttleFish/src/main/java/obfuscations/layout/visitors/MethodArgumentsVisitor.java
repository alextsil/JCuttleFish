package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class MethodArgumentsVisitor
{

    private String obfuscatedVarName;

    private AST ast;

    private final Logger logger = LoggerFactory.getLogger( MethodArgumentsVisitor.class );

    public MethodArgumentsVisitor ( String obfuscatedVarName, AST ast )
    {
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
                        FieldAccess fieldAccess = ModifyAst.thisifySimpleName( this.ast, simpleName );
                        arguments.set( i, fieldAccess );
                    }
                }
            } else if ( arguments.get( i ) instanceof QualifiedName )
            {
                QualifiedName qualifiedName = ( QualifiedName ) arguments.get( i );
                //Check if obfuscated before thisifying
                if ( qualifiedName.getQualifier().getFullyQualifiedName().equals( obfuscatedVarName ) )
                {
                    arguments.set( i, ModifyAst.thisifyQualifiedName( ast, qualifiedName ) );
                }
            }
        }
        return false;
    }
}
