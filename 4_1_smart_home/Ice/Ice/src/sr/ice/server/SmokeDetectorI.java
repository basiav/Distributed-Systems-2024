package sr.ice.server;

import Demo.Info;
import Demo.InfoKey;
import com.zeroc.Ice.Current;

public class SmokeDetectorI extends DetectorI {
    double alarmVolume;

    public SmokeDetectorI(String location) {
        super(location);
    }

    public SmokeDetectorI(String location, double alarmVolume) {
        super(location);
        this.alarmVolume = alarmVolume;
    }

    @Override
    public Info getInfo(Current current) {
        Info info = super.getInfo(current);

        info.moreInfo.put(InfoKey.Location, location + "%");
        return info;
    }
}
