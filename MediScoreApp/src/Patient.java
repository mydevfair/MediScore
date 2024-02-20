import java.util.HashMap;
import java.util.Map;

public class Patient {
    // Enumerations for AirOrOxygen and Consciousness with their respective values
    public enum AirOrOxygen {
        AIR(0), OXYGEN(2);
        private final int respValue;

        AirOrOxygen(int respValue) {
            this.respValue = respValue;
        }

        public int getRespValue() {
            return this.respValue;
        }
    }

    public enum Consciousness {
        ALERT(0), CVPU(3);
        private final int consciousValue;

        Consciousness(int consciousValue) {
            this.consciousValue = consciousValue;
        }

        public int getConsciousValue() {
            return this.consciousValue;
        }
    }

    // Attributes of the Patient class
    private String name;
    private AirOrOxygen airOrOxygen;
    private Integer respirationRange;
    private Integer spo2;
    private float temperature;
    private int airOrOxygenObs;
    private int consciousnessObs;
    private float CBG;
    private boolean fasting;
    Map<String, Integer> individualScores = new HashMap<>();
    Map<String, Integer> previousMediScores = new HashMap<>();

    // Constructor for the Patient class
    public Patient(String name, int airOrOxygenObs, int consciousnessObs,
                   Integer respirationRange, Integer spo2, float temperature, float CBG, boolean fasting) {
        this.name = name;
        this.airOrOxygenObs = airOrOxygenObs;
        this.consciousnessObs = consciousnessObs;
        this.respirationRange = respirationRange;
        this.spo2 = spo2;
        this.temperature = Math.round(temperature * 10) / 10.0f; // Rounding the temperature to 1 decimal place
        this.CBG = Math.round(CBG * 10) / 10.0f;
        this.fasting = fasting;
    }

    public void updatePatient(int airOrOxygenObs, int consciousnessObs,
                              Integer respirationRange, Integer spo2, float temperature, float CBG, boolean fasting) {
        this.airOrOxygenObs = airOrOxygenObs;
        this.consciousnessObs = consciousnessObs;
        this.respirationRange = respirationRange;
        this.spo2 = spo2;
        this.temperature = Math.round(temperature * 10) / 10.0f; // Rounding the temperature to 1 decimal place
        this.CBG = Math.round(CBG * 10) / 10.0f;
        this.fasting = fasting;
    }

    public String getName() {
        return name;
    }

    public AirOrOxygen getAirOrOxygen() {
        return airOrOxygen;
    }

    // Method to add the previous MediScore for the patient

    public void addPreviousMediScore(Patient patient) {
        int finalScore;
        name = patient.getName();
        finalScore = patient.individualScores.get("Final Score");
        previousMediScores.put("Final Score", finalScore);
    }

    public void scoreAlert(Patient patient) {
        if (previousMediScores.isEmpty()) {
            System.out.println(patient.getName() + " Has no previous score to compare with.");
        } else {
            int previousScore = patient.previousMediScores.get("Final Score");
            int newScore = patient.individualScores.get("Final Score");
            int difference = newScore - previousScore;
            if (previousMediScores.isEmpty()) {
                System.out.println(patient.getName() + " Has no previous score to compare with.");
            } else if (difference > 2) {
                System.out.println("ALERT!!!! " + patient.getName() + " MediScore has changed by more than 2 points.");
            } else {
                System.out.println(patient.getName() + " MediScore has not changed by more than 2 points.");
            }
        }
    }


    public void printTable(Patient patient) {
        System.out.println(patient);
    }

    public void clearMap(Map<String, Integer> map) {
        map.clear();
    }

    // Method to calculate the MediScore for the patient.
    // It gets individual scores from separate methods and then adds them up to get the final score.
    // It also stores the individual scores in a map.
    public void calculateMediScore(Patient patient) {
        clearMap(individualScores);
        individualScores.put("Air or Oxygen Score", calculateAirOrOxygenScore(patient));
        individualScores.put("Consciousness Score", calculateConsciousnessScore(patient));
        individualScores.put("Respiration Range Score", calculateRespirationRateScore(patient));
        individualScores.put("SpO2 Score", calculateSpo2Score(patient));
        individualScores.put("Temperature Score", calculateTemperatureScore(patient));
        individualScores.put("CBG Score", calculateCBGScore(patient));
        int finalScore = 0;
        for (Integer score : individualScores.values()) {
            finalScore += score;
        }
        individualScores.put("Final Score", finalScore);
        printTable(patient);
        scoreAlert(patient);
        addPreviousMediScore(patient);
    }

    // Methods to calculate individual scores for each attribute of the patient
    // These methods are called by the calculateMediScore method

    // The following method calculate the Air or Oxygen score for the patient
    private static int calculateAirOrOxygenScore(Patient patient) {
        if (patient.airOrOxygenObs == 0) {
            return AirOrOxygen.AIR.getRespValue();
        } else {
            return AirOrOxygen.OXYGEN.getRespValue();
        }
    }

    // The following method calculates the consciousness score for the patient
    private static int calculateConsciousnessScore(Patient patient) {
        if (patient.consciousnessObs == 0) {
            return Consciousness.ALERT.getConsciousValue();
        } else {
            return Consciousness.CVPU.getConsciousValue();
        }
    }

    // The following method calculates the respiration rate score for the patient
    private static int calculateRespirationRateScore(Patient patient) {
        int respirationRateScore;
        if (patient.respirationRange >= 25) {
            respirationRateScore = 3;
        } else if (patient.respirationRange >= 21) {
            respirationRateScore = 2;
        } else if (patient.respirationRange >= 12) {
            respirationRateScore = 0;
        } else if (patient.respirationRange >= 9) {
            respirationRateScore = 1;
        } else {
            respirationRateScore = 3;
        }
        return respirationRateScore;
    }

    // The following methods calculate the SpO2 score for the patient
    // I used a switch statement to calculate the score based on whether
    // the patient is breathing air or oxygen
    private static int calculateSpo2Score(Patient patient) {
        return switch (patient.airOrOxygenObs) {
            case 0 -> calculateSpo2ScoreForAir(patient);
            case 2 -> calculateSpo2ScoreForOxygen(patient);
            default -> throw new java.lang.IllegalStateException("Unexpected value: " + patient.airOrOxygenObs);

        };
    }

    // The following method calculates the SpO2 score for the patient when they are breathing air
    private static int calculateSpo2ScoreForAir(Patient patient) {
        int spo2Score;
        if (patient.spo2 >= 93) {
            spo2Score = 0;
        } else if (patient.spo2 >= 86) {
            spo2Score = 1;
        } else if (patient.spo2 >= 84) {
            spo2Score = 2;
        } else {
            spo2Score = 3;
        }
        return spo2Score;
    }

    // The following method calculates the SpO2 score for the patient when they are breathing oxygen
    private static int calculateSpo2ScoreForOxygen(Patient patient) {
        int spo2Score;
        if (patient.spo2 >= 97) {
            spo2Score = 0;
        } else if (patient.spo2 >= 93) {
            spo2Score = 1;
        } else if (patient.spo2 >= 88) {
            spo2Score = 2;
        } else {
            spo2Score = 3;
        }
        return spo2Score;
    }

    // The following method calculates the temperature score for the patient
    private static int calculateTemperatureScore(Patient patient) {
        int temperatureScore;
        if (patient.temperature >= 39.1) {
            temperatureScore = 2;
        } else if (patient.temperature >= 38.1) {
            temperatureScore = 1;
        } else if (patient.temperature >= 36.1) {
            temperatureScore = 0;
        } else if (patient.temperature >= 35.1) {
            temperatureScore = 1;
        } else {
            temperatureScore = 3;
        }
        return temperatureScore;
    }
    private static int calculateCBGScore(Patient patient) {
        if (patient.fasting) {
            return calculateCBGFasting(patient);
        } else {
            return calculateCBGNotFasting(patient);
        }
    }
    private static int calculateCBGFasting(Patient patient) {
        int CBGScore = 0;
        if (patient.CBG >= 6.0) {
            CBGScore = 3;
        } else if (patient.CBG >= 5.5) {
            CBGScore = 2;
        } else if (patient.CBG >= 4.0) {
            CBGScore = 0;
        } else if (patient.CBG >= 3.5 && patient.CBG < 3.9) {
            CBGScore = 2;
        } else if (patient.CBG <= 3.5) {
            CBGScore = 2;
        }
        return CBGScore;
    }
    private static int calculateCBGNotFasting(Patient patient) {
        int CBGScore = 0;
        if (patient.CBG >= 9.0) {
            CBGScore = 3;
        } else if (patient.CBG >= 7.9) {
            CBGScore = 2;
        } else if (patient.CBG >= 5.9 && patient.CBG < 7.8) {
            CBGScore = 0;
        } else if (patient.CBG >= 4.5) {
            CBGScore = 2;
        } else if (patient.CBG <= 4.5) {
            CBGScore = 3;
        }
        return CBGScore;
    }

    // The following methods are used to generate comments for the patient's attributes
    // These methods are called by the toString method of the Patient class

    public String AirOrOxygenComment() {
        if (airOrOxygenObs == 0) {
            return "The patient is breathing air, and does not require supplementary oxygen.";
        } else {
            return "The patient requires supplementary oxygen.";
        }
    }

    public String ConsciousnessComment() {
        if (consciousnessObs == 0) {
            return "The patient is conscious";
        } else {
            return "The patient is unconscious or confused.";
        }
    }

    // The following method generates a comment for the SpO2 attribute
    public String Spo2Comment(Patient patient) {
        return switch (patient.airOrOxygenObs) {
            case 0 -> Spo2CommentForAir();
            case 2 -> Spo2CommentForOxygen();
            default -> throw new java.lang.IllegalStateException("Unexpected value: " + patient.airOrOxygenObs);
        };
    }

    // The following method generates a comment for the SpO2 attribute when the patient is breathing air
    public String Spo2CommentForAir() {
        if (spo2 >= 97) {
            return "As the patient is breathing air, this is a normal range.";
        } else if (spo2 >= 95) {
            return "As the patient is breathing air, this is a normal range.";
        } else if (spo2 == 94) {
            return "As the patient is breathing air, this is a normal range.";
        } else if (spo2 >= 88) {
            return "This is a normal range for patients breathing either air or oxygen.";
        } else if (spo2 >= 86) {
            return "This is a slightly low range for patients breathing either air or oxygen.";
        } else if (spo2 >= 84) {
            return "This is a low range for patients breathing either air or oxygen.";
        } else {
            return "The patient's oxygen saturation is dangerously low.";
        }
    }

    // The following method generates a comment for the SpO2 attribute when the patient is breathing oxygen
    public String Spo2CommentForOxygen() {
        if (spo2 >= 97) {
            return "As the patient is breathing oxygen, this is very elevated.";
        } else if (spo2 >= 95) {
            return "As the patient is breathing oxygen, this is elevated.";
        } else if (spo2 >= 93) {
            return "As the patient is breathing oxygen, this is slightly elevated.";
        } else if (spo2 >= 88) {
            return "This is a normal range for patients breathing either air or oxygen.";
        } else if (spo2 >= 86) {
            return "This is a slightly low range for patients breathing either air or oxygen.";
        } else if (spo2 >= 84) {
            return "This is a low range for patients breathing either air or oxygen.";
        } else {
            return "This is a very low range for patients breathing either air or oxygen.";
        }
    }
    public String CBGComment() {
        if (fasting) {
            if (CBG >= 6.0) {
                return "The patient is fasting and their blood sugar is high.";
            } else if (CBG >= 5.5) {
                return "The patient is fasting and their blood sugar is slightly high.";
            } else if (CBG >= 4.0) {
                return "The patient is fasting and their blood sugar is normal.";
            } else if (CBG >= 3.5 && CBG < 3.9) {
                return "The patient is fasting and their blood sugar is slightly low.";
            } else {
                return "The patient is fasting and their blood sugar is low.";
            }
        } else {
            if (CBG >= 9.0) {
                return "The patient isn't fasting so their blood sugar is high.";
            } else if (CBG >= 7.9) {
                return "The patient isn't fasting so their blood sugar is slightly high.";
            } else if (CBG >= 5.9 && CBG < 7.8) {
                return "The patient isn't fasting so their blood sugar is normal.";
            } else if (CBG >= 4.5) {
                return "The patient isn't fasting so their blood sugar is slightly low.";
            } else {
                return "The patient isn't fasting so their blood sugar is low.";
            }
        }
    }
    // Override the toString method to display the patient's attributes and their scores with comments
    // The method also displays the patients name and the final score for the patient
    @Override
    public String toString() {
        String format = "| %-17s | %-11s | %-5s | %-75s |\n";
        String line = "+-------------------+-------------+-------+-----------------------------------------------------------------------------+\n";
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append(line);
        sb.append(String.format(format, "Property", "Observation", "Score", "Comment"));
        sb.append(line);
        sb.append(String.format(format, "Air or Oxygen", airOrOxygenObs, individualScores.get("Air or Oxygen Score"), AirOrOxygenComment()));
        sb.append(line);
        sb.append(String.format(format, "Consciousness", consciousnessObs , individualScores.get("Consciousness Score"), ConsciousnessComment()));
        sb.append(line);
        sb.append(String.format(format, "Respiration Range", respirationRange, individualScores.get("Respiration Range Score"), ""));
        sb.append(line);
        sb.append(String.format(format, "SpO2", spo2, individualScores.get("SpO2 Score"), Spo2Comment(this)));
        sb.append(line);
        sb.append(String.format(format, "Temperature", temperature, individualScores.get("Temperature Score"), ""));
        sb.append(line);
        sb.append(String.format(format, "CBG", CBG, individualScores.get("CBG Score"), CBGComment()));
        sb.append(line);
        sb.append("The patients final Medi score is ").append(individualScores.get("Final Score")).append("\n");
        return sb.toString();
    }
}
