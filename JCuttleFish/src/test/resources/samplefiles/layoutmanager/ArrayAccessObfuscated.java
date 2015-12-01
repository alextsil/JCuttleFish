package test;

public class ArrayAccess
{

    private int a[] = new int[ 2 ];

    public void go ()
    {
        try
        {
            System.out.println( "Access element three :" + a[ 3 ] );
        } catch ( ArrayIndexOutOfBoundsException e )
        {
            System.out.println( "Exception thrown  :" + e );
        } finally
        {
            a[ 0 ] = 6;
            System.out.println( "First element value: " + a[ 0 ] );
            System.out.println( "The finally statement is executed" );
        }
    }
}