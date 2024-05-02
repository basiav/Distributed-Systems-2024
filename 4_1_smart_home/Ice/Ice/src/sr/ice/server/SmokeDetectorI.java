package sr.ice.server;

<<<<<<< HEAD
import Demo.Info;
import Demo.InfoKey;
import com.zeroc.Ice.Current;

=======
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
public class SmokeDetectorI extends DetectorI {
    double alarmVolume;

    public SmokeDetectorI(String location) {
        super(location);
    }

    public SmokeDetectorI(String location, double alarmVolume) {
        super(location);
        this.alarmVolume = alarmVolume;
    }
<<<<<<< HEAD

    @Override
    public Info getInfo(Current current) {
        Info info = super.getInfo(current);

        info.moreInfo.put(InfoKey.Location, location + "%");
        return info;
    }
=======
>>>>>>> a96e70dd073a8a75836a0925cb69dd5e9f908ba8
}
