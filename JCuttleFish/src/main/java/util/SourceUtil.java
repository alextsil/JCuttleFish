package util;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.TextEdit;
import pojo.UnitSource;


public class SourceUtil
{

    static public UnitSource replace ( UnitSource unitSource )
    {
        //TODO: only write if there are changes.
        TextEdit edits = unitSource.getCompilationUnit().rewrite( unitSource.getDocument(), null );
        try
        {
            edits.apply( unitSource.getDocument() );
        } catch ( BadLocationException e )
        {
            e.printStackTrace();
        }
        return unitSource;
    }

}
