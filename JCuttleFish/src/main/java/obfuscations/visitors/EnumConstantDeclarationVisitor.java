package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public class EnumConstantDeclarationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;
    private final Logger logger = LoggerFactory.getLogger( EnumConstantDeclarationVisitor.class );

    public EnumConstantDeclarationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( EnumConstantDeclaration enumConstantDeclaration )
    {
        this.callbacks.stream().forEach( c -> c.notify( enumConstantDeclaration ) );
        return false;
    }
}
