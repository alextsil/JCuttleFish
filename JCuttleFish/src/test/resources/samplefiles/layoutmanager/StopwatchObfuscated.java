package test;

public class a
{

    private long a;

    private long b;

    public static final double NANOS_PER_SEC = 1000000000.0;

    public void a ()
    {
        a = System.nanoTime();
    }

    public void b ()
    {
        b = System.nanoTime();
    }

    public double c ()
    {
        return ( b - a ) / NANOS_PER_SEC;
    }

    public String toString ()
    {
        return "elapsed time: " + c() + " seconds.";
    }

    public long d ()
    {
        return ( b - a );
    }
}