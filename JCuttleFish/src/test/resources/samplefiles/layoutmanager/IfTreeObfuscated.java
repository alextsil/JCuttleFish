package test;

public class ifTrees
{

    private int a;
    private int b = 0;

    public void ifTree ()
    {
        if ( this.a < 10 )
        {
            this.b = this.b + 1;
            if ( this.a < 5 )
            {
                this.b = this.b + 1;
                if ( this.a < 3 && this.b <= 0 )
                {
                    this.b = this.b + 1;
                } else if ( this.a < 2 && this.b == 0 )
                {
                    this.b = this.b + 1;
                } else
                {
                    this.b = this.b + 1;
                }
            }
        } else if ( this.a < 20 )
        {
            this.b += 1;
            if ( this.a < 15 )
            {
                this.b = this.b - 1;
            } else if ( this.a >= 15 )
            {
                this.b = this.b + 1;
            }
        } else
        {
            this.b += 1;
            if ( this.a < 25 )
            {
                this.b = this.b - 1;
            } else if ( this.a >= 25 )
            {
                this.b = this.b + 1;
            }
        }
    }
}