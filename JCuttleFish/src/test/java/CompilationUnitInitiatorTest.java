import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;

import parser.ClassVisitor;
import parser.CompilationUnitInitiator;

public class CompilationUnitInitiatorTest {

	@Test
	public void test() {
		String filePath = "C:\\test\\JCuttleFish\\src\\main\\java\\extractor\\PathsExtractor.java";
		File file = new File(filePath);
		CompilationUnitInitiator cuInit = new CompilationUnitInitiator(file);
		CompilationUnit cu = cuInit.fetchCompilationUnit();

		assertNotNull(cu);

		cu.accept(new ClassVisitor()); 
		
	}
}
