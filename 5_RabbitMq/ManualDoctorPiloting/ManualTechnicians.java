package ManualDoctorPiloting;

import src.ExaminationType;
import src.Technician;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ManualTechnicians {
    public static void main(String[] args) throws IOException, TimeoutException {
        Technician t1 = new Technician("T1", ExaminationType.HIP, ExaminationType.KNEE);
        Technician t2 = new Technician("T2", ExaminationType.ELBOW, ExaminationType.HIP);
    }
}
