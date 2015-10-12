package test;

public class Stopwatch
{

    private long a;

    private long b;

    public static final double NANOS_PER_SEC = 1000000000.0;

    public void start ()
    {
        this.a = System.nanoTime();
    }

    public void stop ()
    {
        this.b = System.nanoTime();
    }

    public double time ()
    {
        return ( this.b - this.a ) / NANOS_PER_SEC;
    }

    public String toString ()
    {
        return "elapsed time: " + time() + " seconds.";
    }

    public long timeInNanoseconds ()
    {
        return ( this.b - this.a );
    }
}