public class Main {
    public static void main(String[] args) {
        Patient patient1 = new Patient("Patient 1", Patient.AirOrOxygen.OXYGEN,
                Patient.Consciousness.ALERT, 20, 90, 98.6f);

        System.out.println(patient1);
    }
}
