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
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;


public class FilenameManager
{

    public Collection<UnitNode> obfuscate ( Collection<UnitNode> unitNodeCollection )
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
                        }
                    } );
                } );

        return unitNodeCollection;
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
