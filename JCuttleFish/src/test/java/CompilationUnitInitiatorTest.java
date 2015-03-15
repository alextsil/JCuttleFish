import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;
import parser.CompilationUnitInitiator;
import parser.MethodDeclarationVisitor;

import java.io.File;

import static org.junit.Assert.assertNotNull;

public class CompilationUnitInitiatorTest {

    @Test
    public void test () {
        String filePath = "C:\\test\\JCuttleFish\\src\\main\\java\\extractor\\PathsExtractor.java";
        File file = new File( filePath );
        CompilationUnitInitiator cuInit = new CompilationUnitInitiator( file );
        CompilationUnit cu = cuInit.fetchCompilationUnit();
        MethodDeclarationVisitor mdv = new MethodDeclarationVisitor();

        assertNotNull( cu );

        cu.accept( mdv );
    }
}
