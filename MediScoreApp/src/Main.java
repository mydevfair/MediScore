public class Main {
    public static void main(String[] args) {
        Patient patient1 = new Patient("Patient 1", Patient.AirOrOxygen.AIR,
                Patient.Consciousness.ALERT, 15, 95, 37.15638f);

        Patient patient2 = new Patient("Patient 2", Patient.AirOrOxygen.OXYGEN,
                Patient.Consciousness.CVPU, 17, 80, 37.15638f);

        Patient patient3 = new Patient("Patient 3", Patient.AirOrOxygen.OXYGEN,
                Patient.Consciousness.CVPU, 23, 88, 38.54f);

        patient1.calculateMediScore(patient1);
        patient2.calculateMediScore(patient2);
        patient3.calculateMediScore(patient3);

        System.out.println(patient1);
        System.out.println(patient2);
        System.out.println(patient3);


    }
}
