package obfuscations.layout.visitors;

import obfuscations.layout.callbacks.AstNodeFoundCallback;
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

        //visit qualifier
        Name qualifier = qualifiedName.getQualifier();
        if ( qualifier.isSimpleName() )
        {
            CastToAndVisit.simpleName( qualifier, this.callbacks );
        } else if ( qualifier.isQualifiedName() )
        {
            CastToAndVisit.qualifiedName( qualifier, this.callbacks );
        }
        return false;
    }
}
