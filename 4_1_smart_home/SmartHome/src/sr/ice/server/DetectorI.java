package sr.ice.server;
import com.zeroc.Ice.Current;

import SmartHome.*;


public abstract class DetectorI extends DeviceNonTurnOffI implements IDetector {
    AlarmVolume alarmVolume;

    public DetectorI(String location) {
        super(location);
        this.alarmVolume = AlarmVolume.High;
    }

    public DetectorI(String location, AlarmVolume alarmVolume) {
        super(location);
        this.alarmVolume = alarmVolume;
    }

    @Override
    public String alert(Current current) {
        String alertMsg = "ioioio";
        if (alarmVolume == AlarmVolume.VeryHigh) {
            alertMsg = alertMsg.toUpperCase();
        }
        System.out.println("Alerting with volume " + alarmVolume + "... " + alertMsg);
        return alertMsg;
    }

    @Override
    public Info getInfo(Current current) {
        Info info = super.getInfo(current);
        info.details.put(InfoKey.Volume, alarmVolume.toString());
        return info;
    }
}
