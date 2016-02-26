package test;

public class a
{

    private long a;

    private long b;

    public static final double c = 1000000000.0;

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
        return ( b - a ) / c;
    }

    @Override
    public String toString ()
    {
        return "elapsed time: " + c() + " seconds.";
    }

    public long d ()
    {
        return ( b - a );
    }
}