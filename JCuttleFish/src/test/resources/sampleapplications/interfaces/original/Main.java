
public class Main
{

    public static void main ( String[] args )
    {
        BallisticBicycle ballisticBicycle = new BallisticBicycle();
        ballisticBicycle.printNumberOfGears();
        ballisticBicycle.randomMethodToCheckForNameCollision();
        ballisticBicycle.toString();
        IdealBicycle idealBicycle = new IdealBicycle();
        idealBicycle.printNumberOfGears();
        idealBicycle.randomMethodToCheckForNameCollision();
        idealBicycle.randomMethodToCheckForNameCollision2();
        idealBicycle.toString();
    }

}
