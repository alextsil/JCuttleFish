package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.Expression;
import pojo.ObfuscationInfo;

import java.util.List;


public class ConstructorInvocationVisitor extends ASTVisitor
{

    private ObfuscationInfo obfuscationInfo;

    public ConstructorInvocationVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( ConstructorInvocation constructorInvocation )
    {
        List<Expression> arguments = constructorInvocation.arguments();
        arguments.stream().forEach( a -> new ExpressionVisitor( this.obfuscationInfo ).visit( a ) );
        return false;
    }
}
