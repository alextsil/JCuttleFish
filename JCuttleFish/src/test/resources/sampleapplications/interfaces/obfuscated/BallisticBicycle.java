
public class a implements b
{

    private int a = 18;

    public a ()
    {
    }

    @Override
    public void printNumberOfGears ()
    {
        System.out.println( this.a + "" );
    }

    @Override
    public String toString ()
    {
        return "BallisticBicycle{" +
                "numberOfGears=" + a +
                '}';
    }
}