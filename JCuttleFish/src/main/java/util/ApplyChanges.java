package util;

import org.apache.commons.io.FileUtils;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitSource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


public class ApplyChanges
{

    //Apply changes and write to file.
    static public boolean apply ( UnitSource unitSource )
    {
        //TODO: only write if there are changes.
        TextEdit edits = unitSource.getCompilationUnit().rewrite( unitSource.getDocument(), null );
        try
        {
            edits.apply( unitSource.getDocument() );
            FileUtils.write(
                    //override for local testing
                    new File( "C:/test/JCuttleFish/src/main/java/configuration/ObfuscationEnvironment.java" ),
                    unitSource.getDocument().get(), Charset.defaultCharset() );
        } catch ( BadLocationException e )
        {
            e.printStackTrace();
            return false;
        } catch ( IOException e2 )
        {
            e2.printStackTrace();
            return false;
        }
        return true;

    }

}
