package test;

public class Switch
{

    private int a;

    public void switchStatements ()
    {
        char grade = 'C';

        switch ( grade )
        {
            case 'A':
                System.out.println( "Excellent!" );
                this.a = 5;
                switch ( this.a )
                {
                    case 5:
                        this.a++;
                        break;
                    default:
                        System.out.println( "someValue : " + this.a );
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