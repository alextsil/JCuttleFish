package test;

public class While
{

    private double a;
    private double b;

    public void whileStuff ()
    {
        int loopVal = 0;

        while ( loopVal < 5 )
        {
            while ( a > 0 )
            {
                a = Math.random();
                b = Math.random();
                loopVal++;
            }
            loopVal += 2;
        }
    }
}