package obfuscations.layout.visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import pojo.ObfuscationInfo;
import util.CastToAndVisit;


public class QualifiedNameVisitor extends ASTVisitor
{

    private final ObfuscationInfo obfuscationInfo;

    public QualifiedNameVisitor ( ObfuscationInfo obfuscationInfo )
    {
        this.obfuscationInfo = obfuscationInfo;
    }

    @Override
    public boolean visit ( QualifiedName qualifiedName )
    {
        //visit qualifier
        Name qualifier = qualifiedName.getQualifier();
        if ( qualifier.isSimpleName() )
        {
            CastToAndVisit.simpleName( qualifier, this.obfuscationInfo );
        } else if ( qualifier.isQualifiedName() )
        {
            CastToAndVisit.qualifiedName( qualifier, this.obfuscationInfo );
        }
        return false;
    }
}
