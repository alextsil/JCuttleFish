package parser;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CompilationUnitInitiator {

    private final Logger logger = LoggerFactory.getLogger( CompilationUnitInitiator.class );

    private String sourceFromFile = ""; // Hosts the source code

    public CompilationUnitInitiator ( File file ) {
        javaFileToString( file );
    }

    public CompilationUnitInitiator () {

    }

    public CompilationUnit fetchCompilationUnit () {
        ASTParser parser = ASTParser.newParser( AST.JLS8 );
        parser.setKind( ASTParser.K_COMPILATION_UNIT );
        parser.setSource( this.sourceFromFile.toCharArray() );
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

        this.sourceFromFile = fileData.toString();
    }

    public void injectionAttempt () {
        String filePath = "C:\\test\\JCuttleFish\\src\\main\\java\\extractor\\PathsExtractor.java";
        File file = new File( filePath );
        this.javaFileToString( file );

        CompilationUnit astRoot = this.fetchCompilationUnit();
        Document document = new Document( this.sourceFromFile );

        MethodDeclarationVisitor mdv = new MethodDeclarationVisitor();
        astRoot.accept( mdv );

        ASTRewrite rewriter = ASTRewrite.create( astRoot.getAST() );
        astRoot.recordModifications();

        // for getting insertion position
        TypeDeclaration typeDecl = ( TypeDeclaration ) astRoot.types().get( 0 );
        MethodDeclaration methodDecl = typeDecl.getMethods()[ 0 ];
        Block block = methodDecl.getBody();

        // create new statements for insertion
        MethodInvocation newInvocation = astRoot.getAST().newMethodInvocation();
        newInvocation.setName( astRoot.getAST().newSimpleName( "add" ) );
        Statement newStatement = astRoot.getAST().newExpressionStatement( newInvocation );

        //create ListRewrite
        ListRewrite listRewrite = rewriter.getListRewrite( block, Block.STATEMENTS_PROPERTY );
        listRewrite.insertLast( newStatement, null );

        TextEdit edits = rewriter.rewriteAST( document, null );
        try {
            edits.apply( document );
        } catch ( BadLocationException e ) {
            e.printStackTrace();
        }
        try {
            File targetFile = new File( "C:\\targetfash.java" );
            FileUtils.write( targetFile, document.get() );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public String getSourceFromFile () {
        return sourceFromFile;
    }
}
