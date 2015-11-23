package extractor.filefilters.enums;

public enum SuffixFilters
{
    JAVA( ".java" ),
    XML( ".xml" ),
    INI( ".ini" ),
    FORM( ".form" ),
    JSON( ".json" ),
    TXT( ".txt" );
    private final String suffix;

    SuffixFilters ( String suffix )
    {
        this.suffix = suffix;
    }

    @Override
    public String toString ()
    {
        return this.suffix;
    }
}