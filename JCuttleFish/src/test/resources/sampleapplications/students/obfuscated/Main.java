import java.util.ArrayList;
import java.util.List;


public class a
{

    public static void main ( String[] aa )
    {
        b aaa = new b();
        aaa.d( "John" );
        aaa.b( "3333" );
        aaa.f( "123" );
        aaa.h( 55 );

        b bbb = new b( "2920", "Alex", "123", 55 );

        List<b> ccc = new ArrayList<b>();
        ccc.add( aaa );
        ccc.add( bbb );
    }
}
