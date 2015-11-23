package test;

public class ifTrees
{

    private int age;
    private int count = 0;

    public void ifTree ()
    {
        if ( age < 10 )
        {
            count = count + 1;
            if ( age < 5 )
            {
                count = count + 1;
                if ( age < 3 && count <= 0 )
                {
                    count = count + 1;
                } else if ( age < 2 && count == 0 )
                {
                    count = count + 1;
                } else
                {
                    count = count + 1;
                }
            }
        } else if ( age < 20 )
        {
            count += 1;
            if ( age < 15 )
            {
                count = count - 1;
            } else if ( age >= 15 )
            {
                count = count + 1;
            }
        } else
        {
            count += 1;
            if ( age < 25 )
            {
                count = count - 1;
            } else if ( age >= 25 )
            {
                count = count + 1;
            }
        }
    }
}