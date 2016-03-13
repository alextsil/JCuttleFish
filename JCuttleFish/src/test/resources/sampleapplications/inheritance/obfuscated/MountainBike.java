public class c extends a
{

    // the MountainBike subclass adds one field
    protected int a;

    // the MountainBike subclass has one constructor
    public c ( int aa, int bb, int cc, int dd )
    {
        super( bb, cc, dd );
        a = aa;
    }

    // the MountainBike subclass adds one method
    public void setHeight ( int aa )
    {
        a = aa;
    }

    public void stop ()
    {
        c = 0;
    }
}