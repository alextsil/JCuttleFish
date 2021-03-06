package extractor;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

//This class resolves the path that was given to the constructor, verifies it exists and it's not empty,
//and saves the File instances of all FILES in a List.


public class PathsExtractor
{

    // The list that contains all the File instances from the extracted paths
    private final List<File> targetFilesInstances;
    // User given path name
    private final String pathName;

    public PathsExtractor ( String pathName )
    {
        this.pathName = pathName;
        this.targetFilesInstances = new ArrayList<>();
    }

    public List<File> getFilesInstances ()
    {
        this.extractPathsRaw( this.pathName );
        this.targetFilesInstances.sort( File::compareTo );
        return this.targetFilesInstances;
    }

    public List<File> getFilesInstances ( FileFilter filter )
    {
        this.extractPathsFiltered( this.pathName, filter );
        this.targetFilesInstances.sort( File::compareTo );
        return this.targetFilesInstances;
    }

    // temp copy - workaround
    private void extractPathsRaw ( String pathName )
    {
        File initialFilePath = new File( pathName );
        assert initialFilePath.isDirectory();

        File[] fileInstances = initialFilePath.listFiles();

        for ( File fileInstance : fileInstances )
        {
            if ( fileInstance.isDirectory() )
            {
                // recursive call - contained folder path
                this.extractPathsRaw( fileInstance.getAbsolutePath() );
            } else
            {
                this.targetFilesInstances.add( fileInstance );
            }
        }
    }

    // temp copy - workaround
    private void extractPathsFiltered ( String pathName, FileFilter filter )
    {
        File initialFilePath = new File( pathName );
        assert initialFilePath.isDirectory();

        File[] fileInstances = initialFilePath.listFiles( filter );

        for ( File fileInstance : fileInstances )
        {
            if ( fileInstance.isDirectory() )
            {
                // recursive call - contained folder path
                this.extractPathsFiltered( fileInstance.getAbsolutePath(), filter );
            } else
            {
                this.targetFilesInstances.add( fileInstance );
            }
        }
    }

}