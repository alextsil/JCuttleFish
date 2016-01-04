package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public class ImportDeclarationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;
    private final Logger logger = LoggerFactory.getLogger( ImportDeclarationVisitor.class );

    public ImportDeclarationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( ImportDeclaration importDeclaration )
    {
        this.callbacks.stream().forEach( c -> c.notify( importDeclaration ) );

        return false;
    }
}
