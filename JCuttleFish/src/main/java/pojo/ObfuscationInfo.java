package pojo;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.SimpleName;


public class ObfuscationInfo
{

    private final SimpleName originalVarSimpleName;
    private final String obfuscatedVarName;
    private final AST ast;

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
}
