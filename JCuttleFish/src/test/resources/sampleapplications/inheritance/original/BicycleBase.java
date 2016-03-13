public class BicycleBase
{

    // the Bicycle class has three fields
    protected int cadence;
    protected int gear;
    protected int speed;

    // the Bicycle class has one constructor
    public BicycleBase ( int startCadence, int startSpeed, int startGear )
    {
        gear = startGear;
        cadence = startCadence;
        speed = startSpeed;
    }

    // the Bicycle class has four methods
    public void setCadence ( int newValue )
    {
        cadence = newValue;
    }

    public void setGear ( int newValue )
    {
        gear = newValue;
    }

    public void applyBrake ( int decrement )
    {
        speed -= decrement;
    }

    public void speedUp ( int increment )
    {
        speed += increment;
    }

}