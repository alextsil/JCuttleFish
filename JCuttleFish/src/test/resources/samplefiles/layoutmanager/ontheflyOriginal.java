package test;

import java.util.Random;


public class Die
{

    public static final int DEFAULT_SIDES = 6;
    private static Random ourRandNumGen = new Random();
    private final int iMyNumSides;
    private int iMyResult;

    public Die ()
    {
        this( DEFAULT_SIDES );
    }

    public Die ( int numSides )
    {
        assert numSides > 1 : "Violation of precondition: numSides = " + numSides + "numSides must be greater than 1";

        iMyNumSides = numSides;
        iMyResult = 1;
        assert getResult() == 1 && getNumSides() == numSides;
    }

    public Die ( int numSides, int result )
    {
        assert numSides > 1 && 1 <= result && result <= numSides : "Violation of precondition";

        iMyNumSides = numSides;
        iMyResult = result;
    }

    public int roll ()
    {
        iMyResult = ourRandNumGen.nextInt( iMyNumSides ) + 1;

        assert ( 1 <= getResult() ) && ( getResult() <= getNumSides() );

        return iMyResult;
    }

    public int getNumSides ()
    {
        return iMyNumSides;
    }

    public int getResult ()
    {
        return iMyResult;
    }

    public boolean equals ( Object otherObj )
    {
        boolean result = true;
        if ( otherObj == null )
            result = false;
        else if ( this == otherObj )
            result = true;
        else if ( this.getClass() != otherObj.getClass() )
            result = false;
        else
        {
            Die otherDie = ( Die )otherObj;
            result = this.iMyResult == otherDie.iMyResult
                    && this.iMyNumSides == otherDie.iMyNumSides;
        }
        return result;
    }

    public String toString ()
    {
        return "Num sides " + getNumSides() + " result " + getResult();
    }

}