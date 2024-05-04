package sr.ice.server;
import com.zeroc.Ice.Current;

import java.util.HashMap;
import java.util.Map;
import Demo.*;


public abstract class DetectorI extends DeviceNonTurnOffI implements IDetector {
    AlarmVolume alarmVolume;

    public DetectorI(String location) {
        super(location);
        this.alarmVolume = AlarmVolume.High;
    }

    public DetectorI(String location, AlarmVolume alarmVolume) {
        super(location);
        this.alarmVolume = alarmVolume;
        System.out.println(this.alarmVolume);
    }

    @Override
    public String alert(Current current) {
        String alertMsg = "ioioio";
        if (alarmVolume == AlarmVolume.VeryHigh) {
            System.out.println(alarmVolume);
            alertMsg = alertMsg.toUpperCase();
        }
        return alertMsg;
    }

    @Override
    public Info getInfo(Current current) {
        return new Info(getStatus(), new HashMap<>(Map.of(InfoKey.Location, location)));
    }
}
