package parser;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.internal.core.JavaProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CompilationUnitInitiator {

    private final Logger logger = LoggerFactory.getLogger( CompilationUnitInitiator.class );

    private String fileContents = ""; // Hosts the source code

    public CompilationUnitInitiator ( File file ) {
        javaFileToString( file );
    }

    public CompilationUnit fetchCompilationUnit () {
        ASTParser parser = ASTParser.newParser( AST.JLS8 );
        parser.setKind( ASTParser.K_COMPILATION_UNIT );
        parser.setSource( this.fileContents.toCharArray() );
        parser.setBindingsRecovery( true );
        parser.setUnitName( "JCuttleFish" + System.lineSeparator() + "src" );
        String[] classPath = { System.getenv( "JAVA_HOME" ) + System.lineSeparator() + "bin" };
        String[] srcPath = { "C:\\test\\JCuttleFish\\src" };
        parser.setEnvironment( classPath, srcPath, null, false );

        parser.setResolveBindings( true );

        CompilationUnit cu = ( CompilationUnit ) parser.createAST( null );
        
        return cu;
    }

    // HACK : refactor to use File instead of strings
    // read file content into a string
    public void javaFileToString ( File file ) {
        String fileToString = file.toString();
        StringBuilder fileData = new StringBuilder( 1000 );

        BufferedReader reader = null;

        try {
            FileReader fileReader = new FileReader( fileToString );
            reader = new BufferedReader( fileReader );
            char[] buf = new char[ 2048 ];
            int numRead = 0;
            while ( ( numRead = reader.read( buf ) ) != -1 ) {
                logger.info( "Read " + numRead + " bytes from stream." );
                String readData = String.valueOf( buf, 0, numRead );
                fileData.append( readData );
                buf = new char[ 2048 ];
            }

        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( reader != null )
                try {
                    reader.close();
                } catch ( IOException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }

        this.fileContents = fileData.toString();
    }

    public String getFileContents () {
        return fileContents;
    }
}
