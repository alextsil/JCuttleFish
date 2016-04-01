package obfuscations.layoutobfuscation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitNode;
import util.ObfuscationUtil;
import util.SourceUtil;

import java.util.Collection;


public class LayoutManager
{

    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public Collection<UnitNode> obfuscate ( Collection<UnitNode> unitNodeCollection )
    {
        this.beginRecordingModifications( unitNodeCollection );

        ObfuscationUtil.obfuscateMethodNames( unitNodeCollection );
        ObfuscationUtil.obfuscateAbstractTypeDeclarationsAndReferences( unitNodeCollection );
        ObfuscationUtil.obfuscateClassLocalVarsAndReferences( unitNodeCollection );
        ObfuscationUtil.obfuscateMethodParameters( unitNodeCollection );
        ObfuscationUtil.obfuscateMethodDeclaredVariables( unitNodeCollection );

        this.applyModifications( unitNodeCollection );
        return unitNodeCollection;
    }

    private void beginRecordingModifications ( Collection<UnitNode> unitNodeCollection )
    {
        unitNodeCollection.stream().forEach( UnitNode::recordMofications );
    }

    private void applyModifications ( Collection<UnitNode> unitNodeCollection )
    {
        unitNodeCollection.stream().forEach( un -> SourceUtil.replace( un.getUnitSource() ) );
    }
}
