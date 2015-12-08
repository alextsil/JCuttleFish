package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

import java.util.Collection;
import java.util.List;


public class SuperMethodInvocationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public SuperMethodInvocationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( SuperMethodInvocation superMethodInvocation )
    {
        this.callbacks.stream().forEach( c -> c.notify( superMethodInvocation ) );

        //Rename and thisify arguments
        List<Object> arguments = superMethodInvocation.arguments();
        //ModifyAst.renameMethodInvocationArguments( arguments, this.obfuscationInfo );
        new MethodArgumentsVisitor( this.callbacks ).visit( arguments );
        return false;
    }
}
