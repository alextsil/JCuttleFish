package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayAccess;
import pojo.ObfuscationInfo;


public class ArrayAccessVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public ArrayAccessVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( ArrayAccess arrayAccess )
    {
        new ExpressionVisitor( this.obfuscationInfo ).preVisit2( arrayAccess.getArray() );
        return false;
    }
}
