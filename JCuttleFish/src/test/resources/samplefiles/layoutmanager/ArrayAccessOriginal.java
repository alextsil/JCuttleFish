package test;

public class ArrayAccess
{

    private int alpha[] = new int[ 2 ];

    public void go ()
    {
        try
        {
            System.out.println( "Access element three :" + alpha[ 3 ] );
        } catch ( ArrayIndexOutOfBoundsException e )
        {
            System.out.println( "Exception thrown  :" + e );
        } finally
        {
            alpha[ 0 ] = 6;
            System.out.println( "First element value: " + alpha[ 0 ] );
            System.out.println( "The finally statement is executed" );
        }
    }
}