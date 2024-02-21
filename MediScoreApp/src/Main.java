
public class Main {

    public static void main(String[] args) {
        // Create 3 patients
        Patient patient1 = new Patient("Patient 1", 2,
                0, 20, 95, 37.15638f, 7.0F, true);

        Patient patient2 = new Patient("Patient 2", 0,
                2, 17, 80, 37.15638f, 9.0F, true);

        Patient patient3 = new Patient("Patient 3", 2,
                1, 23, 88, 38.54f, 6.0F, true);
        // Calculate MediScore for each patient
        patient1.calculateMediScore(patient1);
        patient2.calculateMediScore(patient2);
        patient3.calculateMediScore(patient3);
        // Update patient1's data and calculate MediScore again
        patient1.updatePatient(0, 3, 30, 70, 33.15638f, 3.9F, false);
        // Calculate MediScore for each patient again
        patient1.calculateMediScore(patient1);
        patient2.calculateMediScore(patient2);
        patient3.calculateMediScore(patient3);


    }
}
