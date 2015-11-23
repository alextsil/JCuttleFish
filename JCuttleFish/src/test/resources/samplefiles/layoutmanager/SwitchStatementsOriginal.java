package test;

public class Switch
{

    private int someValue;

    public void switchStatements ()
    {
        char grade = 'C';

        switch ( grade )
        {
            case 'A':
                System.out.println( "Excellent!" );
                someValue = 5;
                switch ( someValue )
                {
                    case 5:
                        someValue++;
                        break;
                    default:
                        System.out.println( "someValue : " + someValue );
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