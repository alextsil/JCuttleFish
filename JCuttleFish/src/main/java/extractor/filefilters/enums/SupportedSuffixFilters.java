package extractor.filefilters.enums;

public enum SupportedSuffixFilters {
	JAVA(".java"),
	XML(".xml"),
	INI(".ini"),
	FORM(".form"),
	JSON(".json"),
	TXT(".txt");

	private String suffix;
	
	private SupportedSuffixFilters(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String toString() {
		return suffix;
	}
}