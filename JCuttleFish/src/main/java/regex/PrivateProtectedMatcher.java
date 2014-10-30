package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PrivateProtectedMatcher implements IMatcher {
	
	private String pattern = "protected|private";
	private String targetLine;
	private Pattern patImpl;
	private Matcher matcher;
	
	public PrivateProtectedMatcher() {
		patImpl = Pattern.compile(pattern);
	}
	
	
	public String applyMatcher(String targetLine) {
		this.targetLine = targetLine; //def copy
		matcher = patImpl.matcher(this.targetLine);
		this.targetLine = matcher.replaceFirst("public");
		return this.targetLine;
	}
}
