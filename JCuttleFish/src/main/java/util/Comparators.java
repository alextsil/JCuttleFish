package util;

import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import java.io.File;
import java.util.Comparator;


public class Comparators
{

    public static Comparator<VariableDeclarationStatement> byOccurenceOnOriginalFile =
            ( vds1, vds2 ) -> vds1.getStartPosition() - vds2.getStartPosition();
    public static Comparator<File> byDepth =
            ( f1, f2 ) -> getPathDepth( f2) - getPathDepth( f1 );

    private static int getPathDepth ( File file )
    {
        if ( file.getParentFile() != null )
        {
            if ( file.getParentFile().isDirectory() )
            {
                return 1 + getPathDepth( file.getParentFile() );
            }
        }
        return 0;
    }
}
