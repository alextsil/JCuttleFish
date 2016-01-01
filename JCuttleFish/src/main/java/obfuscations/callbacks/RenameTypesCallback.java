package obfuscations.callbacks;

import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RenameNodeUtil;


public class RenameTypesCallback extends AstNodeFoundCallback
{

    private String typeIdentifier;
    private String obfuscatedName;
    private final Logger logger = LoggerFactory.getLogger( RenameTypesCallback.class );

    public RenameTypesCallback ( String typeIdentifier, String obfuscatedName )
    {
        this.typeIdentifier = typeIdentifier;
        this.obfuscatedName = obfuscatedName;
    }

    @Override
    public <V extends ASTNode> void notify ( V v )
    {
        if ( v instanceof SimpleType )
        {
            SimpleType simpleType = ( SimpleType )v;
            RenameNodeUtil.renameIdentifierOccurencesInTypeHierarchy( simpleType, typeIdentifier, obfuscatedName );
        } else if ( v instanceof ParameterizedType )
        {
            ParameterizedType parameterizedType = ( ParameterizedType )v;
            RenameNodeUtil.renameIdentifierOccurencesInTypeHierarchy( parameterizedType, typeIdentifier, obfuscatedName );
        } else if ( v instanceof WildcardType )
        {
            WildcardType wildcardType = ( WildcardType )v;
            RenameNodeUtil.renameIdentifierOccurencesInTypeHierarchy( wildcardType, typeIdentifier, obfuscatedName );
        } else if ( v instanceof ArrayType )
        {
            ArrayType arrayType = ( ArrayType )v;
            RenameNodeUtil.renameIdentifierOccurencesInTypeHierarchy( arrayType, typeIdentifier, obfuscatedName );
        }
    }
}
