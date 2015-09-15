package parser;

import configuration.ObfuscationEnvironment;
import org.eclipse.jdt.core.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pojo.UnitSource;
import util.ReadFileHelper;

import java.io.IOException;
import java.nio.charset.Charset;

public class UnitSourceInitiator {

    private final Logger logger = LoggerFactory.getLogger( UnitSourceInitiator.class );

    private String sourceCode = null;

    public UnitSourceInitiator () {

    }

    public UnitSource fetchUnitSource ( String sourceFilePath ) {
        try {
            this.sourceCode = ReadFileHelper.readFile( sourceFilePath, Charset.forName( "UTF-8" ) );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
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

        CompilationUnit cu = ( CompilationUnit ) parser.createAST( null );
        UnitSource unitSource = new UnitSource( cu, this.sourceCode, sourceFilePath );
        return unitSource;
    }


    //obso. retain for example.
//    private void injectionAttempt () {
//        String filePath = "C:\\test\\JCuttleFish\\src\\main\\java\\extractor\\PathsExtractor.java";
//        File file = new File( filePath );
//        //this.javaFileToString( file );
//
//        CompilationUnit astRoot = this.fetchUnitSource(  "s" );
//        Document document = new Document( "source code here" );
//
////        MethodDeclarationVisitor mdv = new MethodDeclarationVisitor();
////        astRoot.accept( mdv );
//
//        ASTRewrite rewriter = ASTRewrite.create( astRoot.getAST() );
//        astRoot.recordModifications();
//
//        // for getting insertion position
//        TypeDeclaration typeDecl = ( TypeDeclaration ) astRoot.types().get( 0 );
//        MethodDeclaration methodDecl = typeDecl.getMethods()[ 1 ];
//        FieldDeclaration fieldDecl = typeDecl.getFields()[ 0 ];
//        //Block block = methodDecl.getBody();
//
////        VariableDeclarationFragment fragment = typeDecl.getAST().newVariableDeclarationFragment();
////        fragment.setName( typeDecl.getAST().newSimpleName( "pambos" ) );
////        FieldDeclaration parameterField = typeDecl.getAST().newFieldDeclaration( fragment );
//        Modifier protectedModifier = fieldDecl.getAST().newModifier( Modifier.ModifierKeyword.PROTECTED_KEYWORD );
//        fieldDecl.modifiers().clear();
//        fieldDecl.modifiers().add( protectedModifier );
//
//        for ( Object E : fieldDecl.modifiers() ) {
//            logger.info( "parameter field mod is of type : " + E.getClass().toString() + " " + E.toString() );
//        }
//
//        // create new statements for insertion
////        MethodInvocation newInvocation = astRoot.getAST().newMethodInvocation();
////        newInvocation.setName( astRoot.getAST().newSimpleName( "add" ) );
////        Statement newStatement = astRoot.getAST().newExpressionStatement( newInvocation );
//
//        //create ListRewrite
//        //rewriter.remove( methodDecl, null );
//        rewriter.remove( fieldDecl, null );
//        ListRewrite listRewrite = rewriter.getListRewrite( typeDecl, TypeDeclaration.BODY_DECLARATIONS_PROPERTY );
//        listRewrite.insertFirst( fieldDecl, null );
//
//        TextEdit edits = rewriter.rewriteAST( document, null );
//        try {
//            edits.apply( document );
//        } catch ( BadLocationException e ) {
//            logger.error( "Failed to apply edits" );
//            e.printStackTrace();
//        }
//        try {
//            File targetFile = new File( "C:\\injectionResult.java" );
//            FileUtils.write( targetFile, document.get() );
//        } catch ( IOException e ) {
//            logger.error( "Failed to write file" );
//            e.printStackTrace();
//        }
//    }

}
