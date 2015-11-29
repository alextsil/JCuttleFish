package obfuscations.layout;

import org.eclipse.jdt.core.dom.SimpleName;

import java.util.List;


public class MapStatementToSimpleNameCollection
{

    private List<SimpleName> simpleNamesFound;

    public static void addToFoundCollection ( SimpleName simpleName )
    {

    }

    private void addToCollection ( SimpleName simpleName )
    {
        this.simpleNamesFound.add( simpleName );
    }
}
