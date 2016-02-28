package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConvenienceWrappers;

import java.util.Collection;
import java.util.List;


public class TypeDeclarationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    private final Logger logger = LoggerFactory.getLogger( TypeDeclarationVisitor.class );

    public TypeDeclarationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( TypeDeclaration typeDeclaration )
    {
        this.callbacks.stream().forEach( c -> c.notify( typeDeclaration ) );

        ITypeBinding typeBinding = typeDeclaration.resolveBinding();
        if ( typeBinding.isClass() )
        {
            ConvenienceWrappers.getFieldDeclarationsAsList( typeDeclaration ).stream()
                    .forEach( fd -> new FieldDeclarationVisitor( this.callbacks ).visit( fd ) );

            ConvenienceWrappers.getMethodDeclarationsAsList( typeDeclaration ).stream()
                    .forEach( md -> new MethodDeclarationVisitor( this.callbacks ).visit( md ) );

            List superInterfaceTypes = typeDeclaration.superInterfaceTypes();
            superInterfaceTypes.stream()
                    .forEach( sit -> new TypeVisitor( this.callbacks ).visit( ( Type )sit ) );

        } else
        {
            this.logger.info( "Type binding is not a concrete class : " );
        }

        return false;
    }
}
