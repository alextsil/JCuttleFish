package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.SimpleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RenameClassReferenceCallback extends AstNodeFoundCallback
{

    private IBinding classNameBinding;
    private String obfuscatedName;
    private final Logger logger = LoggerFactory.getLogger( RenameClassReferenceCallback.class );

    public RenameClassReferenceCallback ( IBinding classNameBinding, String obfuscatedName )
    {
        this.classNameBinding = classNameBinding;
        this.obfuscatedName = obfuscatedName;
    }

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof SimpleName )
        {
            SimpleName simpleName = ( SimpleName )v;
            IBinding simpleNameBinding = simpleName.resolveBinding();
            if ( simpleNameBinding != null )
            {
                if ( simpleName.resolveBinding().isEqualTo( this.classNameBinding ) )
                {
                    simpleName.setIdentifier( this.obfuscatedName );
                }
            }
        }
    }
}
