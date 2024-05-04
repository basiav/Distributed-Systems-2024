package sr.ice.server;
import SmartHome.*;
import com.zeroc.Ice.Current;

public class SmokeDetectorI extends DetectorI {
    double smokeLevel = 0.00001;

    public SmokeDetectorI(String location) {
        super(location, AlarmVolume.High);
    }

    public SmokeDetectorI(String location, double smokeLevel) {
        super(location);
        this.smokeLevel = smokeLevel;
    }

    @Override
    public Info getInfo(Current current) {
        Info info = super.getInfo(current);
        info.details.put(InfoKey.SmokeLevel, String.valueOf(smokeLevel));
        return info;
    }
}
