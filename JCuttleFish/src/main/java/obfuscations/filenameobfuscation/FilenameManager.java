package obfuscations.filenameobfuscation;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import pojo.UnitNode;
import providers.ObfuscatedNamesProvider;
import util.ConvenienceWrappers;
import util.enums.ObfuscatedNamesVariations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;


public class FilenameManager
{

    private File pathToMain;
    private File rootPath;

    public FilenameManager ( File rootPath )
    {
        this.rootPath = rootPath;
    }

    public Collection<UnitNode> obfuscate ( Collection<UnitNode> unitNodeCollection )
    {
        Collection<UnitNode> obfuscatedUnitNodeCollection;
        obfuscatedUnitNodeCollection = this.obfuscateFilenames( unitNodeCollection );

        this.obfuscateFoldernames();

        return obfuscatedUnitNodeCollection;
    }

    private Collection<UnitNode> obfuscateFilenames ( Collection<UnitNode> unitNodeCollection )
    {
        ObfuscatedNamesProvider obfuscatedNamesProvider = new ObfuscatedNamesProvider();
        Deque<String> obfuscatedNames = obfuscatedNamesProvider.getObfuscatedNames( ObfuscatedNamesVariations.ALPHABET );

        unitNodeCollection.stream()
                .map( UnitNode::getUnitSource )
                .forEach( us -> {
                    List<AbstractTypeDeclaration> abstractTypeDeclarations = us.getCompilationUnit().types();
                    abstractTypeDeclarations.stream().forEach( atd -> {
                        List<String> methodsIdentifiers = ConvenienceWrappers.getMethodDeclarationsAsList( atd )
                                .stream().map( m -> m.getName().getIdentifier() ).collect( Collectors.toList() );
                        if ( !methodsIdentifiers.contains( "main" ) )
                        {
                            us.setFile( this.renameJavaFileTo( us.getFile(), obfuscatedNames.pollFirst() ) );
                        } else
                        {
                            this.pathToMain = us.getFile();
                        }
                    } );
                } );
        return unitNodeCollection;
    }

    private void obfuscateFoldernames ()
    {
        try
        {
            FolderVisitor folderVisitor = new FolderVisitor( this.rootPath, this.createExcludedFolderNamesList() );
            Files.walkFileTree( this.rootPath.toPath(), folderVisitor );
            List<File> targetFolders = folderVisitor.getTargetFolders();
            //// TODO: 9/3/2016 Sort by depth and rename
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    //This will produce a list of all folders between the class containing main() and the root path.
    private List<File> createExcludedFolderNamesList ()
    {
        List<File> excludedFolders = new ArrayList<>();

        File target = this.pathToMain;
        do
        {
            target = target.getParentFile();
            excludedFolders.add( target );
        } while ( target.equals( this.rootPath ) );

        return excludedFolders;
    }

    private File renameJavaFileTo ( File file, String newNameWithoutPrefix )
    {
        Path renamedFile = null;
        try
        {
            renamedFile = Files.move( file.toPath(), file.toPath().resolveSibling( newNameWithoutPrefix + ".java" ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
        assert renamedFile != null;
        return renamedFile.toFile();
    }

}
