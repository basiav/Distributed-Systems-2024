package sr.ice.server;
import Demo.*;
import Demo.IDetector;
import com.zeroc.Ice.Current;

import java.util.HashMap;
import java.util.Map;



public abstract class DetectorI extends DeviceNonTurnOffI implements IDetector {
    public DetectorI(String location) {
        super(location);
    }

    @Override
    public String alert(Current current) {
        return "IOIOIOIOIO";
    }

    @Override
    public Info getInfo(Current current) {
        return new Info(getStatus(), new HashMap<>(Map.of(InfoKey.Location, location)));
    }
}
