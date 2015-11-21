package pojo;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.SimpleName;


public class ObfuscationInfo
{

    private SimpleName originalVarSimpleName;

    private String obfuscatedVarName;

    private AST ast;

    public ObfuscationInfo ( SimpleName originalVarSimpleName, String obfuscatedVarName, AST ast )
    {
        this.originalVarSimpleName = originalVarSimpleName;
        this.obfuscatedVarName = obfuscatedVarName;
        this.ast = ast;
    }

    public SimpleName getOriginalVarSimpleName ()
    {
        return originalVarSimpleName;
    }

    public String getObfuscatedVarName ()
    {
        return obfuscatedVarName;
    }

    public AST getAst ()
    {
        return ast;
    }
}
