package extractor.filefilters;

public enum SupportedSuffixFilters {
	JAVA,
	XML,
	INI,
	FORM,
	JSON,
	TXT;


	@Override
	public String toString() {
		switch(this) {
			case JAVA:
				return ".java";
			case XML:
				return ".xml";
			case INI:
				return ".ini";
			case FORM:
				return ".form";
			case JSON:
				return ".json";
			case TXT:
				return ".txt";
				
			default:
				return null;
		}
	}
}