package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.SimpleName;


public class SimpleNameVisitor extends ASTVisitor
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    public SimpleNameVisitor ( SimpleName originalVarSimpleName, String obfuscatedVarName )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
    }

    @Override
    public boolean visit ( SimpleName simpleName )
    {
        IVariableBinding varBinding = ( IVariableBinding ) simpleName.resolveBinding();
        if ( varBinding.isField() )
        {
            ModifyAst.renameSimpleName( simpleName, this.originalVarSimpleName, this.obfuscatedVarName );
        }
        return super.visit( simpleName );
    }
}
