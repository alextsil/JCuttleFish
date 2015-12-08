public class Test
{

    public void test ()
    {
        int aaa = 5;
        String bbb = "test";
        String ccc = "test2322";

        ccc = bbb + aaa;
        ccc = null;
    }

    public void test2 ( String aa, String bb )
    {
        test( aa, bb );
    }

    public static double var ( double[] aa, int bb, int cc )
    {
        int aaa = cc - bb + 1;
        if ( bb < 0 || cc >= aa.length || bb > cc )
            throw new IndexOutOfBoundsException( "Subarray indices out of bounds" );
        if ( aaa == 0 ) return Double.NaN;
        double bbb = mean( aa, bb, cc );
        double ccc = 0.0;
        for ( int i = bb; i <= cc; i++ )
        {
            ccc += ( aa[ i ] - bbb ) * ( aa[ i ] - bbb );
        }
        return ccc / ( aaa - 1 );
    }

    public static void show ( boolean[][] aa, boolean bb )
    {
        int aaa = aa.length;
        StdDraw.setXscale( -1, aaa );
        StdDraw.setYscale( -1, aaa );
        for ( int i = 0; i < aaa; i++ )
            for ( int j = 0; j < aaa; j++ )
                if ( aa[ i ][ j ] == bb )
                    StdDraw.filledSquare( j, aaa - i - 1, .5 );
    }
}