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
            while ( this.a > 0 )
            {
                this.a = Math.random();
                this.b = Math.random();
                loopVal++;
            }
            loopVal += 2;
        }
    }
}