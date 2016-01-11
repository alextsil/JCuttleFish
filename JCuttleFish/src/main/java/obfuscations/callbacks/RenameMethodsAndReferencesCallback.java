package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.*;
import pojo.UnitNode;
import util.ObfuscationUtil;

import java.util.Collection;


public class RenameMethodsAndReferencesCallback extends AstNodeFoundCallback
{

    private Collection<UnitNode> unitNodes;

    public RenameMethodsAndReferencesCallback ( Collection<UnitNode> unitNodes )
    {
        this.unitNodes = unitNodes;
    }

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof TypeDeclaration )
        {
            ObfuscationUtil.renameMethodsAndReferences( ( AbstractTypeDeclaration )v, this.unitNodes );
        } else if ( v instanceof EnumDeclaration )
        {
            ObfuscationUtil.renameMethodsAndReferences( ( AbstractTypeDeclaration )v, this.unitNodes );
        } else if ( v instanceof AnnotationTypeDeclaration )
        {
            throw new RuntimeException( "not implemented" );
        }
    }
}
