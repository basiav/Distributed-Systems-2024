package sr.ice.server;

import Demo.DetectorSafetyException;
import Demo.IDetector;
<<<<<<< HEAD
import Demo.Info;
import Demo.InfoKey;
import com.zeroc.Ice.Current;

import java.util.HashMap;
import java.util.Map;

=======
import com.zeroc.Ice.Current;

>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
public abstract class DetectorI extends DeviceNonTurnOffI implements IDetector {
    public DetectorI(String location) {
        super(location);
    }

    @Override
    public String alert(Current current) {
        return "IOIOIOIOIO";
    }
<<<<<<< HEAD

    @Override
    public Info getInfo(Current current) {
        return new Info(getStatus(), new HashMap<>(Map.of(InfoKey.Location, location)));
    }
=======
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
}
