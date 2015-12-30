package parser;

import configuration.ConfigurationEnvironment;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitSource;
import providers.FileSourceCodeProvider;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UnitSourceInitiator
{

    private final Logger logger = LoggerFactory.getLogger( UnitSourceInitiator.class );
    private String sourceCode = null;

    public UnitSourceInitiator ()
    {
    }

    public UnitSource fetchUnitSource ( File file )
    {
        this.sourceCode = this.getSourceCodeFromFile( file );
        ConfigurationEnvironment configurationEnvironment = ConfigurationEnvironment.getInstance();
        ASTParser parser = ASTParser.newParser( AST.JLS8 );
        parser.setKind( ASTParser.K_COMPILATION_UNIT );
        parser.setSource( this.sourceCode.toCharArray() );
        parser.setBindingsRecovery( true );

        parser.setUnitName( configurationEnvironment.getRelativeSourcePath() );
        String[] classPath = configurationEnvironment.getClasspath();
        String[] srcPath = configurationEnvironment.getAbsoluteSourcePath();
        parser.setEnvironment( classPath, srcPath, null, false );

        parser.setResolveBindings( true );

        CompilationUnit cu = ( CompilationUnit )parser.createAST( null );
        return new UnitSource( cu, file, this.sourceCode );
    }

    public Collection<UnitSource> fetchUnitSourceCollection ( Collection<File> files )
    {
        List<UnitSource> unitSources = files.stream()
                .map( this::fetchUnitSource )
                .collect( Collectors.toList() );
        return unitSources;
    }

    private String getSourceCodeFromFile ( File file )
    {
        FileSourceCodeProvider fileSourceCodeProvider = new FileSourceCodeProvider();
        return fileSourceCodeProvider.get( file );
    }
}
