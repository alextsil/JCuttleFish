package util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;


public class BackupFilesHelper
{

    static public void backupFiles ( File sourceDir, File destDirBase )
    {
        try
        {
            if ( destDirBase.isDirectory() )
            {
                String destinationPath = FilenameUtils.concat( destDirBase.getPath(), "JCuttleFishBackup" );
                final File destinationDir = new File( destinationPath );
                FileUtils.forceMkdir( destinationDir );
                destinationDir.setWritable( true );
                FileUtils.copyDirectory( sourceDir, destinationDir );
            } else
            {
                throw new RuntimeException( "Given backup directory does not exist." );
            }
        } catch ( IOException ioex1 )
        {
            ioex1.printStackTrace();
            throw new RuntimeException( "Cannot complete backup." );
        }
    }
}
