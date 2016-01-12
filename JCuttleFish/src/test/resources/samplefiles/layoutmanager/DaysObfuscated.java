package test;

public enum a
{
    a( 1 ),
    b( 2 ),
    c( 3 ),
    d( 4 ),
    e( 5 ),
    f( 6 ),
    g( 7 );
    private int a;

    private a ( int aa )
    {
        this.a = aa;
    }

    public int a ()
    {
        return this.a;
    }

    @Override
    public String toString ()
    {
        switch ( this )
        {
            case a:
                return "Monday: " + a;
            case b:
                return "Tuesday: " + a;
            case c:
                return "Wednesday: " + a;
            case d:
                return "Thursday: " + a;
            case e:
                return "Friday: " + a;
            case f:
                return "Saturday: " + a;
            case g:
                return "Sunday: " + a;
            default:
                return "Default";
        }
    }
}