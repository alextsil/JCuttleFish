public class ClassMain
{

    public static void main ( String[] args )
    {
        BicycleBase bicycleBase = new BicycleBase( 1, 0, 1 );
        MountainBike mountainBike = new MountainBike( 15, 1, 0, 1 );

        bicycleBase.speedUp( 15 );
        bicycleBase.applyBrake( 5 );

        mountainBike.speedUp( 25 );
        mountainBike.applyBrake( 10 );
    }
}