package test;

public class a
{

    private int a;
    private int b = 0;

    public void a ()
    {
        if ( a < 10 )
        {
            b = b + 1;
            if ( a < 5 )
            {
                b = b + 1;
                if ( a < 3 && b <= 0 )
                {
                    b = b + 1;
                } else if ( a < 2 && b == 0 )
                {
                    b = b + 1;
                } else
                {
                    b = b + 1;
                }
            }
        } else if ( a < 20 )
        {
            b += 1;
            if ( a < 15 )
            {
                b = b - 1;
            } else if ( a >= 15 )
            {
                b = b + 1;
            }
        } else
        {
            b += 1;
            if ( a < 25 )
            {
                b = b - 1;
            } else if ( a >= 25 )
            {
                b = b + 1;
            }
        }
    }
}