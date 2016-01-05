package test;

public class a
{

    private String a;
    private String b;
    private int c = 0;

    public void a ()
    {
        BufferedReader aaa = new BufferedReader( new InputStreamReader(
                System.in ) );

        boolean bbb = true;

        while ( bbb )
        {
            System.out.print( "Enter an integer:" );
            a = aaa.readLine();
            StringTokenizer ccc = new StringTokenizer( a );
            b = "";

            while ( bbb && ccc.hasMoreTokens() )
            {
                try
                {
                    b = ccc.nextToken();
                    c = Integer.parseInt( b );
                    bbb = false;
                } catch ( NumberFormatException n )
                {
                    System.out.println( "The value in \"" + b + "\" is not an integer" );
                }
            }
        }
    }
}