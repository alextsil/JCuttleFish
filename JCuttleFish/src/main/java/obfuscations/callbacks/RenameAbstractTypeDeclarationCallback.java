package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitNode;
import util.ObfuscationUtil;

import java.util.Collection;


public class RenameAbstractTypeDeclarationCallback extends AstNodeFoundCallback
{

    private String obfuscatedName;

    private Collection<UnitNode> unitNodes;

    private final Logger logger = LoggerFactory.getLogger( RenameAbstractTypeDeclarationCallback.class );

    public RenameAbstractTypeDeclarationCallback ( String obfuscatedName, Collection<UnitNode> unitNodes )
    {
        this.obfuscatedName = obfuscatedName;
        this.unitNodes = unitNodes;
    }

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof TypeDeclaration )
        {
            if ( ( ( TypeDeclaration )v ).isInterface() )
            {
                //todo : call interface obfuscation logic
                logger.info( "TypeDeclaration isInterface is true : " + v.toString() );
            } else
            {
                ObfuscationUtil.renameAbstractTypeDeclarationAndReferences( ( TypeDeclaration )v, this.obfuscatedName, this.unitNodes );
            }
        }
    }
}
