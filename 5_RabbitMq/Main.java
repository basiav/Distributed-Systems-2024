
public class Main {
    public static void main(String[] args) throws Exception {
        Doctor d1 = new Doctor("D1");
        Doctor d2 = new Doctor("D2");
        Technician t1 = new Technician(ExaminationType.HIP, ExaminationType.KNEE);
        Technician t2 = new Technician(ExaminationType.ELBOW, ExaminationType.HIP);
        d1.start();
        d2.start();
    }
}
