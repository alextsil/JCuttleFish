package obfuscations.layout;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import parser.UnitSourceInitiator;
import pojo.UnitSource;

import java.io.File;
import java.util.List;

public class LayoutManager {

    private final Logger logger = LoggerFactory.getLogger( LayoutManager.class );

    public boolean obfuscate ( List<File> targetFiles ) {
        for ( File file : targetFiles ) {
            UnitSourceInitiator initiator = new UnitSourceInitiator();
            logger.info( "path (String) extracted from File is : " + file.getPath() );
            UnitSource unitSource = initiator.fetchUnitSource( file.getPath() );
            Document document = new Document( unitSource.getSourceCode() );
            CompilationUnit cu = unitSource.getCompilationUnit();
            ASTRewrite rewriter = ASTRewrite.create( cu.getAST() );
            cu.recordModifications();

            if ( !cu.types().isEmpty() ) {

                SimpleName originalVarName = null;

                //replace "if" with "typedecl.resolvebindings().isClass()";
                TypeDeclaration typeDecl = ( TypeDeclaration ) cu.types().get( 0 );
                if ( typeDecl.getFields().length != 0 ) {
                    for ( FieldDeclaration fieldDeclaration : typeDecl.getFields() ) {
                        //get(0) translates to get variable declaration fragment.
                        VariableDeclarationFragment vdf = (VariableDeclarationFragment) fieldDeclaration.fragments().get(0);

                        originalVarName = vdf.getName();
                        logger.info( "original var name is : " + originalVarName.getFullyQualifiedName() );
                        //rewriter.remove( fieldDeclaration, null );
                        //ListRewrite listRewrite = rewriter.getListRewrite( typeDecl, TypeDeclaration.BODY_DECLARATIONS_PROPERTY );
                        //listRewrite.insertFirst( fieldDeclaration, null );
                    }
                    MethodDeclaration methodDeclaration = typeDecl.getMethods()[0];
                    ExpressionStatement es = (ExpressionStatement)methodDeclaration.getBody().statements().get( 0 );
                    Assignment assignment = (Assignment)es.getExpression();
                    FieldAccess fieldAccess = (FieldAccess) assignment.getLeftHandSide();
                    SimpleName varUsageName = fieldAccess.getName();


                    logger.info( "usage var name is : " + varUsageName.getFullyQualifiedName() );
                }
            }
        }
        return true;
    }
}
