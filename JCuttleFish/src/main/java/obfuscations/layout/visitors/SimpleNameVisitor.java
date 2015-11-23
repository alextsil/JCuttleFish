package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleNameVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;
    private String obfuscatedVarName;
    private final Logger logger = LoggerFactory.getLogger( SimpleNameVisitor.class );

    public SimpleNameVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
    }

    @Override
    public boolean visit ( SimpleName simpleName )
    {
        IVariableBinding varBinding = ( IVariableBinding )simpleName.resolveBinding();
        if ( varBinding == null )
        {
            logger.info( "Variable binding is null. Returning." );
            return false;
        }
        if ( varBinding.isField() )
        {
            ModifyAst.renameSimpleName( simpleName, this.originalVarSimpleName, this.obfuscatedVarName );
        }
        return false;
    }
}
