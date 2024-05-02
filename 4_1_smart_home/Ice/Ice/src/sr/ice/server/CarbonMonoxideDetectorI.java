package sr.ice.server;

public class CarbonMonoxideDetectorI extends DetectorI{
    double alarmVolume;

    public CarbonMonoxideDetectorI(String location) {
        super(location);
    }

    public CarbonMonoxideDetectorI(String location, double alarmVolume) {
        super(location);
        this.alarmVolume = alarmVolume;
    }
}
