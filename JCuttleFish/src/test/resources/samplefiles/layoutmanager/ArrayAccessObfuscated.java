package test;

public class ArrayAccess
{

    private int a[] = new int[ 2 ];

    public void go ()
    {
        try
        {
            System.out.println( "Access element three :" + this.a[ 3 ] );
        } catch ( ArrayIndexOutOfBoundsException e )
        {
            System.out.println( "Exception thrown  :" + e );
        } finally
        {
            this.a[ 0 ] = 6;
            System.out.println( "First element value: " + this.a[ 0 ] );
            System.out.println( "The finally statement is executed" );
        }
    }
}