public class Test
{

    public void test ()
    {
        int var1 = 5;
        String var2 = "test";
        String var3 = "test2322";

        var3 = var2 + var1;
        var3 = null;
    }

    public void test2 ( String var1, String var2 )
    {
        test( var1, var2 );
    }

    public static double var ( double[] a, int lo, int hi )
    {
        int length = hi - lo + 1;
        if ( lo < 0 || hi >= a.length || lo > hi )
            throw new IndexOutOfBoundsException( "Subarray indices out of bounds" );
        if ( length == 0 ) return Double.NaN;
        double avg = mean( a, lo, hi );
        double sum = 0.0;
        for ( int i = lo; i <= hi; i++ )
        {
            sum += ( a[ i ] - avg ) * ( a[ i ] - avg );
        }
        return sum / ( length - 1 );
    }

    public static void show ( boolean[][] a, boolean which )
    {
        int N = a.length;
        StdDraw.setXscale( -1, N );
        StdDraw.setYscale( -1, N );
        for ( int i = 0; i < N; i++ )
            for ( int j = 0; j < N; j++ )
                if ( a[ i ][ j ] == which )
                    StdDraw.filledSquare( j, N - i - 1, .5 );
    }
}