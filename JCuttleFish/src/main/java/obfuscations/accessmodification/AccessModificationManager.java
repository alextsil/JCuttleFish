package obfuscations.accessmodification;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.CompilationUnitInitiator;
import pojo.UnitSource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class AccessModificationManager {

    private final Logger logger = LoggerFactory.getLogger( AccessModificationManager.class );

    public AccessModificationManager () {
    }

    //TODO : replace if checks with .isClass() function of eclipse JDT (binding recovery)
    public boolean obfuscate ( List<File> targetFiles ) {
        for ( File file : targetFiles ) {
            CompilationUnitInitiator initiator = new CompilationUnitInitiator();
            logger.info( "path (String) extracted from File is : " + file.getPath() );
            UnitSource unitSource = initiator.fetchUnitSource( file.getPath() );
            Document document = new Document( unitSource.getSourceCode() );
            CompilationUnit cu = unitSource.getCompilationUnit();
            ASTRewrite rewriter = ASTRewrite.create( cu.getAST() );
            cu.recordModifications();

            if ( !cu.types().isEmpty() ) {

                TypeDeclaration typeDecl = ( TypeDeclaration ) cu.types().get( 0 );
                if ( typeDecl.getFields().length != 0 ) {
                    for ( FieldDeclaration fieldDeclaration : typeDecl.getFields() ) {

                        Modifier protectedModifier = fieldDeclaration.getAST().newModifier( Modifier.ModifierKeyword.PUBLIC_KEYWORD );
                        fieldDeclaration.modifiers().clear();
                        fieldDeclaration.modifiers().add( protectedModifier );

                        rewriter.remove( fieldDeclaration, null );
                        ListRewrite listRewrite = rewriter.getListRewrite( typeDecl, TypeDeclaration.BODY_DECLARATIONS_PROPERTY );
                        listRewrite.insertFirst( fieldDeclaration, null );
                    }
                    TextEdit edits = rewriter.rewriteAST( document, null );
                    try {
                        edits.apply( document );
                    } catch ( BadLocationException e ) {
                        logger.error( "Failed to apply edits" );
                        e.printStackTrace();
                    }
                    try {
                        //overwrites the target file. this will change.
                        //TODO : move into a class that handles the file writing.
                        File targetFile = file;
                        FileUtils.write( targetFile, document.get() );
                    } catch ( IOException e ) {
                        logger.error( "Failed to overwrite file" );
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

}
