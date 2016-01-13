package obfuscations.layoutobfuscation;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitNode;
import util.ConvenienceWrappers;
import util.ObfuscationUtil;
import util.SourceUtil;

import java.util.Collection;
import java.util.Collections;


public class LayoutManager
{

    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public Collection<UnitNode> obfuscate ( Collection<UnitNode> unitNodeCollection )
    {
        unitNodeCollection.stream().forEach( UnitNode::recordMofications );

        ObfuscationUtil.obfuscateAbstractTypeDeclarationNames( unitNodeCollection );
        ObfuscationUtil.obfuscateMethodNames( unitNodeCollection );
        ObfuscationUtil.obfuscateClassLocalVarsAndReferences( unitNodeCollection );

        for ( UnitNode unitNode : unitNodeCollection )
        {
            //FIXME: Quickfix. Repair when compilation units with more than 1 Type are added.
            Collection<MethodDeclaration> methods = Collections.emptyList();
            if ( !unitNode.getUnitSource().getCompilationUnit().types().isEmpty() )
            {
                methods = ConvenienceWrappers
                        .getMethodDeclarationsAsList( ( AbstractTypeDeclaration )unitNode.getUnitSource()
                                .getCompilationUnit().types().get( 0 ) );
            }
            methods.stream().forEach( m -> {
                ObfuscationUtil.obfuscateMethodParameters( unitNode, m );
                ObfuscationUtil.obfuscateMethodDeclaredVariables( unitNode, m );
            } );
        }

        unitNodeCollection.stream().forEach( un -> SourceUtil.replace( un.getUnitSource() ) );
        return unitNodeCollection;
    }
}
