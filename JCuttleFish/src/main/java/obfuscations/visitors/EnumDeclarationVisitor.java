package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConvenienceWrappers;

import java.util.Collection;
import java.util.List;


public class EnumDeclarationVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;
    private final Logger logger = LoggerFactory.getLogger( EnumDeclarationVisitor.class );

    public EnumDeclarationVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( EnumDeclaration enumDeclaration )
    {
        this.callbacks.stream().forEach( c -> c.notify( enumDeclaration ) );

        ConvenienceWrappers.getFieldDeclarationsAsList( enumDeclaration ).stream()
                .forEach( fd -> new FieldDeclarationVisitor( this.callbacks ).visit( fd ) );

        ConvenienceWrappers.getMethodDeclarationsAsList( enumDeclaration ).stream()
                .forEach( md -> new MethodDeclarationVisitor( this.callbacks ).visit( md ) );
        List superInterfaceTypes = enumDeclaration.superInterfaceTypes();

        superInterfaceTypes.stream()
                .forEach( sit -> new TypeVisitor( this.callbacks ).visit( ( Type )sit ) );

        List<EnumConstantDeclaration> enumConstants = enumDeclaration.enumConstants();
        enumConstants.stream().forEach( c -> new EnumConstantDeclarationVisitor( this.callbacks ).visit( c ) );

        return false;
    }
}
