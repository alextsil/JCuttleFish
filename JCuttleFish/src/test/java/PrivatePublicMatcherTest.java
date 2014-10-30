import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import regex.PrivateProtectedMatcher;


public class PrivatePublicMatcherTest {

	private PrivateProtectedMatcher matcher;
	private String resultLine;
	private String targetLine;
	
	@Before
	public void setUp() throws Exception {
		matcher = new PrivateProtectedMatcher();
	}

	@After
	public void tearDown() throws Exception {
		//dokimase me matcher null k xwris g n deis an stackarei kati sto matcher k girnaei oti nanai
	}

	@Test
	public void doubleUseTest() {
		targetLine = "private int count;";
		resultLine = matcher.applyMatcher(targetLine);
		assertEquals("public int count;", resultLine);
	
		targetLine = "private final static String myString = \"do not do this\"";
		resultLine = matcher.applyMatcher(targetLine);
		assertEquals("public final static String myString = \"do not do this\"", resultLine);
	}
	
	@Test
	public void privateStaticTest() {
		targetLine = "private static int count;";
		resultLine = matcher.applyMatcher(targetLine);
		assertEquals("public static int count;", resultLine);
	}
	
	@Test
	public void privateTest() {
		targetLine = "private int count;";
		resultLine = matcher.applyMatcher(targetLine);
		assertEquals("public int count;", resultLine);
	}
	
	@Test
	public void protectedTest() {
		targetLine = "protected int count;";
		resultLine = matcher.applyMatcher(targetLine);
		assertEquals("public int count;", resultLine);
	}

	@Test
	public void publicTest() {
		targetLine = "public int count;";
		resultLine = matcher.applyMatcher(targetLine);
		assertEquals("public int count;", resultLine);
	}
	
	@Test
	public void publicCapitalizedTest() {
		targetLine = "Public int countSkip;";
		resultLine = matcher.applyMatcher(targetLine);
		assertEquals("Public int countSkip;", resultLine);
	}
	
	@Test
	public void privateCapitalizedTest() {
		targetLine = "Private int countSkip;";
		resultLine = matcher.applyMatcher(targetLine);
		assertEquals("Private int countSkip;", resultLine);
	}
	
	@Test
	public void protectedCapitalizedTest() {
		targetLine = "Protected int countSkip;";
		resultLine = matcher.applyMatcher(targetLine);
		assertEquals("Protected int countSkip;", resultLine);
	}
}
