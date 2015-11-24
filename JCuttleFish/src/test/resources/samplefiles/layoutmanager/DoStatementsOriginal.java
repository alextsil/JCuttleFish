package test;

public class DoWhile
{

    private double random;
    private double random2;

    public void doWhileStuff ()
    {
        int loopVal = 0;

        do
        {
            do
            {
                random = Math.random();
                random2 = Math.random();
                loopVal++;
            } while ( random > 0 );
            loopVal += 2;
        }
        while ( loopVal < 5 );
    }
}