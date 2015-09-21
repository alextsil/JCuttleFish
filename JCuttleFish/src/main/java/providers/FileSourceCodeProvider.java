package providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;


public class FileSourceCodeProvider implements SourceCodeProvider<File>
{

    private final Logger logger = LoggerFactory.getLogger( FileSourceCodeProvider.class );

    //TODO: handle file errors/special cases
    @Override
    public String get ( File file )
    {
        logger.debug( "path in : " + file.toPath().toString() );
        byte[] encoded = null;
        try
        {
            encoded = Files.readAllBytes( file.toPath() );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

        return new String( encoded, Charset.forName( "UTF-8" ) );
    }
}