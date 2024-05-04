package sr.ice.server;
import SmartHome.*;

import com.zeroc.Ice.Current;

public class CarbonMonoxideDetectorI extends DetectorI {
    double carbonMonoxideLevel = 0.0001;

    public CarbonMonoxideDetectorI(String location) {
        super(location, AlarmVolume.VeryHigh);
    }

    public CarbonMonoxideDetectorI(String location, double carbonMonoxideLevel) {
        super(location);
        this.carbonMonoxideLevel = carbonMonoxideLevel;
    }

    @Override
    public Info getInfo(Current current) {
        Info info = super.getInfo(current);
        info.details.put(InfoKey.CarbonMonoxideLevel, String.valueOf(carbonMonoxideLevel));
        return info;
    }
}
