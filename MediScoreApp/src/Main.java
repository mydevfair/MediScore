public class Main {
    public static void main(String[] args) {
        Patient patient1 = new Patient("Patient 1", Patient.AirOrOxygen.OXYGEN,
                Patient.Consciousness.CVPU1, 23, 95, 38.5F);

        patient1.mediScoreFunction(patient1);

        System.out.println(patient1);
    }
}
