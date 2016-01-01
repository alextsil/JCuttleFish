package test;

public class a
{

    private double a;
    private double b;

    public void doWhileStuff ()
    {
        int aaa = 0;

        do
        {
            do
            {
                a = Math.random();
                b = Math.random();
                aaa++;
            } while ( a > 0 );
            aaa += 2;
        }
        while ( aaa < 5 );
    }
}