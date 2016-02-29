
public class BallisticBicycle implements Bicycle
{

    private int numberOfGears = 18;

    public BallisticBicycle ()
    {
    }

    public void randomMethodToCheckForNameCollision ()
    {

    }

    @Override
    public void printNumberOfGears ()
    {
        System.out.println( this.numberOfGears + "" );
    }

    @Override
    public String toString ()
    {
        return "BallisticBicycle{" +
                "numberOfGears=" + numberOfGears +
                '}';
    }
}