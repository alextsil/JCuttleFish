package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
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
                //todo : call interface methods obfuscation logic
                logger.info( "TypeDeclaration isInterface is true : " + v.toString() );
            } else
            {
                ObfuscationUtil.renameMethodsAndReferences( ( AbstractTypeDeclaration )v, this.unitNodes );
            }
        }
    }
}
