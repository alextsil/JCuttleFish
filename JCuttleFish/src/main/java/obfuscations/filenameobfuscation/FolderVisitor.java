package obfuscations.filenameobfuscation;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;


public class FolderVisitor extends SimpleFileVisitor<Path>
{

    private File rootPath;
    private List<File> excludedFolders;
    private List<File> targetFolders = new ArrayList<>();

    public FolderVisitor ( File rootPath, List<File> excludedFolders )
    {
        this.rootPath = rootPath;
        this.excludedFolders = excludedFolders;
    }

    @Override
    public FileVisitResult preVisitDirectory ( Path dir, BasicFileAttributes attrs ) throws IOException
    {
        if ( this.rootPath.getName().equals( dir.toFile().getName() ) )
        {
            return FileVisitResult.CONTINUE;
        }
        if ( this.excludedFolders.contains( dir.toFile() ) )
        {
            return FileVisitResult.SKIP_SUBTREE;
        }
        if ( dir.toFile().isDirectory() )
        {
            this.targetFolders.add( dir.toFile() );
            return FileVisitResult.CONTINUE;
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile ( Path file, BasicFileAttributes attrs ) throws IOException
    {
        return FileVisitResult.CONTINUE;
    }

    public List<File> getTargetFolders ()
    {
        return this.targetFolders;
    }
}
