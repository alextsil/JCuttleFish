package obfuscations.layout.visitors;

import obfuscations.layout.ModifyAst;
import obfuscations.layout.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;

import java.util.Collection;
import java.util.List;


public class ClassInstanceCreationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public ClassInstanceCreationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( ClassInstanceCreation classInstanceCreation )
    {
        this.callbacks.stream().forEach( c -> c.notify( classInstanceCreation ) );

        //Rename and thisify arguments
        List<Object> arguments = classInstanceCreation.arguments();
        ModifyAst.renameMethodInvocationArguments( arguments, this.callbacks );
        new MethodArgumentsVisitor( this.callbacks ).visit( arguments );
        return false;
    }
}
