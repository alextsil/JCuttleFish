package test;

public class KeybIntReader
{

    private String a;
    private String b;
    private int c = 0;

    public void read ()
    {
        BufferedReader br = new BufferedReader( new InputStreamReader(
                System.in ) );

        boolean cont = true;

        while ( cont )
        {
            System.out.print( "Enter an integer:" );
            a = br.readLine();
            StringTokenizer st = new StringTokenizer( a );
            b = "";

            while ( cont && st.hasMoreTokens() )
            {
                try
                {
                    b = st.nextToken();
                    c = Integer.parseInt( b );
                    cont = false;
                } catch ( NumberFormatException n )
                {
                    System.out.println( "The value in \"" + b + "\" is not an integer" );
                }
            }
        }
    }
}