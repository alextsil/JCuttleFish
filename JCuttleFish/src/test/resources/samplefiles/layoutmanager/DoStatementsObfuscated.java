package test;

public class DoWhile
{

    private double a;
    private double b;

    public void doWhileStuff ()
    {
        int loopVal = 0;

        do
        {
            do
            {
                a = Math.random();
                b = Math.random();
                loopVal++;
            } while ( a > 0 );
            loopVal += 2;
        }
        while ( loopVal < 5 );
    }
}