package test;

public enum Days
{
    MONDAY( 1 ),
    TUESDAY( 2 ),
    WEDNESDAY( 3 ),
    THURSDAY( 4 ),
    FRIDAY( 5 ),
    SATURDAY( 6 ),
    SUNDAY( 7 );
    private int value;

    private Days ( int value )
    {
        this.value = value;
    }

    public int getValue ()
    {
        return this.value;
    }

    @Override
    public String toString ()
    {
        switch ( this )
        {
            case MONDAY:
                return "Monday: " + value;
            case TUESDAY:
                return "Tuesday: " + value;
            case WEDNESDAY:
                return "Wednesday: " + value;
            case THURSDAY:
                return "Thursday: " + value;
            case FRIDAY:
                return "Friday: " + value;
            case SATURDAY:
                return "Saturday: " + value;
            case SUNDAY:
                return "Sunday: " + value;
            default:
                return "Default";
        }
    }
}