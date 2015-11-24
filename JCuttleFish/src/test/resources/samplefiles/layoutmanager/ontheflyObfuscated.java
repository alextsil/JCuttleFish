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
            this.a = br.readLine();
            StringTokenizer st = new StringTokenizer( this.a );
            this.b = "";

            while ( cont && st.hasMoreTokens() )
            {
                try
                {
                    this.b = st.nextToken();
                    this.c = Integer.parseInt( this.b );
                    cont = false;
                } catch ( NumberFormatException n )
                {
                    System.out.println( "The value in \"" + this.b + "\" is not an integer" );
                }
            }
        }
    }
}