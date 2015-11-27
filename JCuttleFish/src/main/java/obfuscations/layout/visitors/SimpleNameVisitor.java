package obfuscations.layout.visitors;

import obfuscations.layout.AstNodeFoundCallback;
import obfuscations.layout.SimpleNameNodeFoundCallBack;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.SimpleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;


public class SimpleNameVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;
    private final Logger logger = LoggerFactory.getLogger( SimpleNameVisitor.class );

    public SimpleNameVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( SimpleName simpleName )
    {
        Optional<SimpleNameNodeFoundCallBack> callBackOptional =
                this.callbacks.stream()
                        .filter( c -> c instanceof SimpleNameNodeFoundCallBack )
                        .map( c -> ( SimpleNameNodeFoundCallBack )c ).findFirst();

        SimpleNameNodeFoundCallBack callBack = callBackOptional.orElseThrow( RuntimeException::new );
        callBack.addToCollection( simpleName );
        return false;
    }
}
