package test;

public class KeybIntReader
{

    private String s1;
    private String s2;
    private int num = 0;

    public void read ()
    {
        BufferedReader br = new BufferedReader( new InputStreamReader(
                System.in ) );

        boolean cont = true;

        while ( cont )
        {
            System.out.print( "Enter an integer:" );
            s1 = br.readLine();
            StringTokenizer st = new StringTokenizer( s1 );
            s2 = "";

            while ( cont && st.hasMoreTokens() )
            {
                try
                {
                    s2 = st.nextToken();
                    num = Integer.parseInt( s2 );
                    cont = false;
                } catch ( NumberFormatException n )
                {
                    System.out.println( "The value in \"" + s2 + "\" is not an integer" );
                }
            }
        }
    }
}