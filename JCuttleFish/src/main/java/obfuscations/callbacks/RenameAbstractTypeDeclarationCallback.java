package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
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
        if ( v instanceof TypeDeclaration )
        {
            ObfuscationUtil.renameTypeDeclarationAndReferences( ( TypeDeclaration )v, this.obfuscatedName, this.unitNodes );
        } else if ( v instanceof EnumDeclaration )
        {
            //
        } else if ( v instanceof AnnotationTypeDeclaration )
        {
            //
        }
    }
}
