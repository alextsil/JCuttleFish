package obfuscations.visitors;

import obfuscations.callbacks.AstNodeFoundCallback;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.QualifiedName;
import util.CastToAndVisit;

import java.util.Collection;


public class QualifiedNameVisitor extends ASTVisitor
{

    private Collection<AstNodeFoundCallback> callbacks;

    public QualifiedNameVisitor ( Collection<AstNodeFoundCallback> callbacks )
    {
        this.callbacks = callbacks;
    }

    @Override
    public boolean visit ( QualifiedName qualifiedName )
    {
        this.callbacks.stream().forEach( c -> c.notify( qualifiedName ) );

        Name qualifier = qualifiedName.getQualifier();
        if ( qualifier.isSimpleName() )
        {
            CastToAndVisit.simpleName( qualifier, this.callbacks );
        } else if ( qualifier.isQualifiedName() )
        {
            CastToAndVisit.qualifiedName( qualifier, this.callbacks );
        }

        CastToAndVisit.simpleName( qualifiedName.getName(), this.callbacks );

        return false;
    }
}
