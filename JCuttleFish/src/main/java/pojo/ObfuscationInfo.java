package pojo;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.SimpleName;

import java.util.Collection;
import java.util.HashSet;


public class ObfuscationInfo
{

    private final SimpleName originalVarSimpleName;
    private final String obfuscatedVarName;
    private final AST ast;
    private final Collection<AstNodeFoundCallback> callbacks = new HashSet<>();

    public ObfuscationInfo ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    public SimpleName getOriginalVarSimpleName ()
    {
        return this.originalVarSimpleName;
    }

    public String getObfuscatedVarName ()
    {
        return this.obfuscatedVarName;
    }

    public AST getAst ()
    {
        return this.ast;
    }

    public Collection<AstNodeFoundCallback> getCallbacks ()
    {
        return callbacks;
    }

    public void registerCallback ( AstNodeFoundCallback callback )
    {
        this.callbacks.add( callback );
    }
}
