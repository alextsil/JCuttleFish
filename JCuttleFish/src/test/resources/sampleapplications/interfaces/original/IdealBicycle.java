
public class IdealBicycle implements Bicycle
{

    private int numberOfGears = 24;

    public IdealBicycle ()
    {
    }

    public void randomMethodToCheckForNameCollision ()
    {

    }

    public void randomMethodToCheckForNameCollision2 ()
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
        return "IdealBicycle{" +
                "numberOfGears=" + numberOfGears +
                '}';
    }
}