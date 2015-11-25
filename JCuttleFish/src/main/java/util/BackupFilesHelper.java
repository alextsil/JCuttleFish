package util;

import extractor.PathsExtractor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;


public class BackupFilesHelper
{

    static public boolean backupFiles ( File sourceDir, File destDir )
    {
        PathsExtractor pathsExtractor = new PathsExtractor( sourceDir.toString() );
        List<File> sourceFiles = pathsExtractor.getFilesInstances();

        //delete backup dir if exists
//        try
//        {
//            if ( destDir.isDirectory() )
//            {
//                FileUtils.forceDelete( destDir );
//            }
//        } catch ( IOException ioex1 )
//        {
//            ioex1.printStackTrace();
//            return false;
//        }
        //loop all to replace root directory
        for ( File sourceFile : sourceFiles )
        {
            String sourcePath = sourceFile.toString();
            String destPath = sourcePath.replace( sourceDir.toString(), destDir.toString() );
            try
            {
                //create parent dirs for each file.
                Files.createDirectories( new File( destPath ).toPath() );
                Files.copy( sourceFile.toPath(), new File( destPath ).toPath(), StandardCopyOption.REPLACE_EXISTING );
            } catch ( IOException ioex2 )
            {
                ioex2.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
