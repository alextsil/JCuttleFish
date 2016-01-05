package test;

public class a
{

    private int a[] = new int[ 2 ];

    public void a ()
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