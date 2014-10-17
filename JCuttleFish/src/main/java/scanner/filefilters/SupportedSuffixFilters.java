package scanner.filefilters;

public enum SupportedSuffixFilters {
	JAVA,
	XML;


	@Override
	public String toString() {
		switch(this) {
			case JAVA:
				return ".java";
			case XML:
				return ".xml";
			default:
				return null;
		}
	}
}