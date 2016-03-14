public class MountainBike extends BicycleBase
{

    // the MountainBike subclass adds one field
    protected int seatHeight;

    // the MountainBike subclass has one constructor
    public MountainBike ( int startHeight, int startCadence, int startSpeed, int startGear )
    {
        super( startCadence, startSpeed, startGear );
        seatHeight = startHeight;
    }

    // the MountainBike subclass adds one method
    public void setHeight ( int newValue )
    {
        seatHeight = newValue;
    }

    @Override
    public void applyBrake ( int decrement )
    {
        //
    }
}