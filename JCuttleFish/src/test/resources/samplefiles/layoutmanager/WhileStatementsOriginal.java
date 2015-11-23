package test;

public class While
{

    private double random;
    private double random2;

    public void whileStuff ()
    {
        int loopVal = 0;

        while ( loopVal < 5 )
        {
            while ( random > 0 )
            {
                random = Math.random();
                random2 = Math.random();
                loopVal++;
            }
            loopVal += 2;
        }
    }
}