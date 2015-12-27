package pojo;

import org.eclipse.jdt.core.dom.ASTNode;

import java.util.HashMap;
import java.util.List;


public class UnitNode
{

    private UnitSource unitSource;
    private HashMap<String, List<ASTNode>> collectedNodes;

    public UnitNode ( UnitSource unitSource, HashMap<String, List<ASTNode>> collectedNodes )
    {
        this.unitSource = unitSource;
        this.collectedNodes = collectedNodes;
    }

    public UnitSource getUnitSource ()
    {
        return this.unitSource;
    }

    public HashMap<String, List<ASTNode>> getCollectedNodes ()
    {
        return this.collectedNodes;
    }

    public void recordMofications ()
    {
        this.unitSource.getCompilationUnit().recordModifications();
    }

}
