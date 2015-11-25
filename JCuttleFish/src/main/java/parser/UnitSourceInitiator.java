package parser;

import configuration.ObfuscationEnvironment;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitSource;


public class UnitSourceInitiator
{

    private final Logger logger = LoggerFactory.getLogger( UnitSourceInitiator.class );
    private String sourceCode = null;

    public UnitSourceInitiator ()
    {
    }

    public UnitSource fetchUnitSource ( String sourceCode )
    {
        this.sourceCode = sourceCode;
        ObfuscationEnvironment obfuscationEnvironment = new ObfuscationEnvironment();
        ASTParser parser = ASTParser.newParser( AST.JLS8 );
        parser.setKind( ASTParser.K_COMPILATION_UNIT );
        parser.setSource( this.sourceCode.toCharArray() );
        parser.setBindingsRecovery( true );

        parser.setUnitName( obfuscationEnvironment.getRelativeSourcePath() );
        String[] classPath = obfuscationEnvironment.getClasspath();
        String[] srcPath = obfuscationEnvironment.getAbsoluteSourcePath();
        parser.setEnvironment( classPath, srcPath, null, false );

        parser.setResolveBindings( true );

        CompilationUnit cu = ( CompilationUnit )parser.createAST( null );
        return new UnitSource( cu, this.sourceCode );
    }
}
