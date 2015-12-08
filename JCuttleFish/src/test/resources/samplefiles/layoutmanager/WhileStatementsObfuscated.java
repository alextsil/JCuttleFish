package test;

public class While
{

    private double a;
    private double b;

    public void whileStuff ()
    {
        int aaa = 0;

        while ( aaa < 5 )
        {
            while ( a > 0 )
            {
                a = Math.random();
                b = Math.random();
                aaa++;
            }
            aaa += 2;
        }
    }
}