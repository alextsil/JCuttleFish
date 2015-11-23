package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import pojo.ObfuscationInfo;


public class QualifiedNameVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public QualifiedNameVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( QualifiedName qualifiedName )
    {
        new SimpleNameVisitor( this.obfuscationInfo.getOriginalVarSimpleName(),
                this.obfuscationInfo.getObfuscatedVarName() ).visit( ( SimpleName )qualifiedName.getQualifier() );
        return false;
    }
}
