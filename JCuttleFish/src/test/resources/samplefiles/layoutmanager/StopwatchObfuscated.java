package test;

public class a
{

    private long a;

    private long b;

    public static final double NANOS_PER_SEC = 1000000000.0;

    public void start ()
    {
        a = System.nanoTime();
    }

    public void stop ()
    {
        b = System.nanoTime();
    }

    public double time ()
    {
        return ( b - a ) / NANOS_PER_SEC;
    }

    public String toString ()
    {
        return "elapsed time: " + time() + " seconds.";
    }

    public long timeInNanoseconds ()
    {
        return ( b - a );
    }
}