package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.*;
import pojo.UnitNode;
import util.ObfuscationUtil;

import java.util.Collection;


public class RenameAbstractTypeDeclarationCallback extends AstNodeFoundCallback
{

    private String obfuscatedName;
    private Collection<UnitNode> unitNodes;

    public RenameAbstractTypeDeclarationCallback ( String obfuscatedName, Collection<UnitNode> unitNodes )
    {
        this.obfuscatedName = obfuscatedName;
        this.unitNodes = unitNodes;
    }

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof TypeDeclaration || v instanceof EnumDeclaration || v instanceof AnnotationTypeDeclaration )
        {
            ObfuscationUtil.renameAbstractTypeDeclarationAndReferences( ( AbstractTypeDeclaration )v, this.obfuscatedName, this.unitNodes );
        }
    }
}
