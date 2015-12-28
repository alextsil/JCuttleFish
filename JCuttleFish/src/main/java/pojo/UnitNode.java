package pojo;

import org.eclipse.jdt.core.dom.ASTNode;
import util.NodeIdentification;

import java.util.*;


public class UnitNode
{

    private UnitSource unitSource;
    private Collection<ASTNode> collectedNodes;

    public UnitNode ( UnitSource unitSource, Collection<ASTNode> collectedNodes )
    {
        this.unitSource = unitSource;
        this.collectedNodes = collectedNodes;
    }

    public UnitSource getUnitSource ()
    {
        return this.unitSource;
    }

    public Collection<ASTNode> getCollectedNodes ()
    {
        return this.collectedNodes;
    }

    public Map<String, List<ASTNode>> getCollectedNodesGroupedByIdentifier ()
    {
        Map<String, List<ASTNode>> groupedNodes = new HashMap<>();

        this.collectedNodes.stream()
                .forEach( n -> this.putToMapOrAddToListIfExists( groupedNodes,
                        NodeIdentification.mapNodeToIdentifier( n ), n ) );
        return groupedNodes;
    }

    public void recordMofications ()
    {
        this.unitSource.getCompilationUnit().recordModifications();
    }

    //TODO : generics
    private void putToMapOrAddToListIfExists ( Map<String, List<ASTNode>> map, String identifier, ASTNode node )
    {
        if ( map.containsKey( identifier ) )
        {
            List<ASTNode> list = map.get( identifier );
            list.add( node );
        } else
        {
            List<ASTNode> list = new ArrayList<>();
            list.add( node );
            map.put( identifier, list );
        }
    }
}
