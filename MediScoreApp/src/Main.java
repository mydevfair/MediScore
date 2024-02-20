public class Main {
    public static void main(String[] args) {
        Patient patient1 = new Patient("Patient 1", 2,
                2, 15, 95, 37.15638f);

        Patient patient2 = new Patient("Patient 2", 0,
                2, 17, 80, 37.15638f);

        Patient patient3 = new Patient("Patient 3", 2,
                1, 23, 88, 38.54f);

        patient1.calculateMediScore(patient1);
        patient2.calculateMediScore(patient2);
        patient3.calculateMediScore(patient3);
        patient1.updatePatient(0, 3, 30, 70, 33.15638f);
        patient1.calculateMediScore(patient1);
        patient2.calculateMediScore(patient2);
        patient3.calculateMediScore(patient3);




    }
}
