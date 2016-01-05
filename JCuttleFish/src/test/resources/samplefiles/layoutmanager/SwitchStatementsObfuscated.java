package test;

public class a
{

    private int a;

    public void a ()
    {
        char aaa = 'C';

        switch ( aaa )
        {
            case 'A':
                System.out.println( "Excellent!" );
                a = 5;
                switch ( a )
                {
                    case 5:
                        a++;
                        break;
                    default:
                        System.out.println( "someValue : " + a );
                }
                break;
            case 'B':
            case 'C':
                System.out.println( "Well done" );
                break;
            default:
                System.out.println( "Invalid grade" );
        }
    }
}