package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.ObfuscationInfo;


public class SimpleNameVisitor extends ASTVisitor
{

    private final ObfuscationInfo obfuscationInfo;
    private final Logger logger = LoggerFactory.getLogger( SimpleNameVisitor.class );

    public SimpleNameVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( SimpleName simpleName )
    {
        IVariableBinding varBinding = ( IVariableBinding )simpleName.resolveBinding();
        if ( varBinding == null )
        {
            this.logger.info( "Variable binding is null. Returning." );
            return false;
        }
        if ( varBinding.isField() )
        {
            ModifyAst.renameSimpleName( simpleName, this.obfuscationInfo.getOriginalVarSimpleName(),
                    this.obfuscationInfo.getObfuscatedVarName() );
        }
        return false;
    }
}
