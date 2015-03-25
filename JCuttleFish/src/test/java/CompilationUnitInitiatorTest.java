import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.internal.core.util.SimpleDocument;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;
import org.junit.Test;
import parser.CompilationUnitInitiator;
import parser.MethodDeclarationVisitor;

import java.io.File;
import java.lang.instrument.ClassDefinition;

import static org.junit.Assert.assertNotNull;

public class CompilationUnitInitiatorTest {

    @Test
    public void test () {
        String filePath = "C:\\test\\JCuttleFish\\src\\main\\java\\extractor\\PathsExtractor.java";
        File file = new File( filePath );
        CompilationUnitInitiator cuInit = new CompilationUnitInitiator( file );

        CompilationUnit cu = cuInit.fetchCompilationUnit();
        cu.recordModifications(); //track tis allages p kanw
        MethodDeclarationVisitor mdv = new MethodDeclarationVisitor();
        assertNotNull( cu );
        cu.accept( mdv );

        ASTRewrite rewriter = ASTRewrite.create( cu.getAST() );

        //////////////////////////////////////
        TypeDeclaration typeDeclaration = ( TypeDeclaration ) cu.types().get( 0 );

        SimpleName newName = cu.getAST().newSimpleName( "WTFFFFFFFFFFFFFFFFFFFFF" );

        typeDeclaration.setName( newName );

        // rewriter.replace( oldName, newName, null );
        IDocument sourceDoc = new SimpleDocument( cuInit.getFileContents() );

        TextEdit edits = null;

        try {
            edits = rewriter.rewriteAST();
        } catch ( JavaModelException e ) {
            e.printStackTrace();
        }

        // computation of the new source code
        try {
            edits.apply( sourceDoc );
        } catch ( BadLocationException e ) {
            e.printStackTrace();
        }
        String newSource = sourceDoc.get();
        System.out.print( newSource );
        // cu.accept( mdv );
    }
}
