package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
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

        new TypeVisitor( this.callbacks ).visit( classInstanceCreation.getType() );

        List<Object> arguments = classInstanceCreation.arguments();
        new MethodArgumentsVisitor( this.callbacks ).visit( arguments );
        return false;
    }
}
