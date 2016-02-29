package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitNode;
import util.ObfuscationUtil;

import java.util.Collection;


public class RenameMethodsAndReferencesCallback extends AstNodeFoundCallback
{

    private Collection<UnitNode> unitNodes;

    private final Logger logger = LoggerFactory.getLogger( RenameMethodsAndReferencesCallback.class );

    public RenameMethodsAndReferencesCallback ( Collection<UnitNode> unitNodes )
    {
        this.unitNodes = unitNodes;
    }

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof TypeDeclaration )
        {
            if ( ( ( TypeDeclaration )v ).isInterface() )
            {
                ObfuscationUtil.renameInterfaceMethodsAndReferences( ( TypeDeclaration )v, this.unitNodes );
            } else
            {
                ObfuscationUtil.renameClassMethodsAndReferences( ( TypeDeclaration )v, this.unitNodes );
            }
        }
    }
}
