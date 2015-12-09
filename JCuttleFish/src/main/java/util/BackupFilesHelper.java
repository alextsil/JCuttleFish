package util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;


public class BackupFilesHelper
{

    static public boolean backupFiles ( File sourceDir, File destDir )
    {
        try
        {
            if ( destDir.isDirectory() )
            {
                FileUtils.forceDelete( destDir );
                FileUtils.forceMkdir( destDir );
                destDir.setWritable( true );
            }
        } catch ( IOException ioex1 )
        {
            ioex1.printStackTrace();
            return false;
        }

        try
        {
            FileUtils.copyDirectory( sourceDir, destDir );
        } catch ( IOException e )
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
