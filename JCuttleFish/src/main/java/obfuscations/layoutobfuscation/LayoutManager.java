package obfuscations.layoutobfuscation;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitNode;
import util.ConvenienceWrappers;
import util.ObfuscationUtil;
import util.SourceUtil;

import java.util.Collection;


public class LayoutManager
{

    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public Collection<UnitNode> obfuscate ( Collection<UnitNode> unitNodeCollection )
    {
        unitNodeCollection.stream().forEach( UnitNode::recordMofications );

        ObfuscationUtil.obfuscateClassNames( unitNodeCollection );

        for ( UnitNode unitNode : unitNodeCollection )
        {
            Collection<MethodDeclaration> methods = ConvenienceWrappers
                    .returnMethodDeclarations( unitNode.getUnitSource().getTypeDeclarationIfIsClass() );
            methods.stream().forEach( m -> {
                ObfuscationUtil.obfuscateMethodParameters( unitNode, m );
                ObfuscationUtil.obfuscateMethodDeclaredVariables( unitNode, m );
            } );
            ObfuscationUtil.obfuscateClassLocalVarsAndReferences( unitNode );
        }

        unitNodeCollection.stream().forEach( un -> SourceUtil.replace( un.getUnitSource() ) );
        return unitNodeCollection;

    }
}
