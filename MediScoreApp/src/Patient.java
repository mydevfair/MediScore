import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Patient {

    // Enum to store the air or oxygen status of a patient and its corresponding score
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

    // Enum to store the consciousness status of a patient and its corresponding score
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

    // Attributes
    private final String name;
    private AirOrOxygen airOrOxygen;
    private Consciousness consciousness;

    private Integer respirationRange;

    private Integer spo2;
    private float temperature;

    private int previousScore;

    Map<String, Integer> individualScores = new HashMap<>();

    // Constructor for the Patient class
    public Patient(String name, AirOrOxygen airOrOxygen, Consciousness consciousness, Integer respirationRange, Integer spo2, float temperature) {
        this.name = name;
        this.airOrOxygen = airOrOxygen;
        this.consciousness = consciousness;
        this.respirationRange = respirationRange;
        this.spo2 = spo2;
        this.temperature = Math.round(temperature * 10) / 10.0f; // round to 1 decimal place
    }

    // Getter methods
    public AirOrOxygen getAirOrOxygen() {
        return airOrOxygen;
    }

    public Consciousness getConsciousness() {
        return consciousness;
    }

    /* Method to calculate the Medi score of a patient which call the individual score methods
     * This was originally one big method that calculated the score for each property of the patient
     * I refactored it to call the individual score methods and store the results in a map
     * To make the code more readable and maintainable and to reduce the complexity of the method
     * So that it only calculates the final score of the patient
     */
    public void calculateMediScore(Patient patient) {
        individualScores.put("Air or Oxygen Score", calculateAirOrOxygenScore(patient));
        individualScores.put("Consciousness Score", calculateConsciousnessScore(patient));
        individualScores.put("Respiration Range Score", calculateRespirationRateScore(patient));
        individualScores.put("SpO2 Score", calculateSpo2Score(patient));
        individualScores.put("Temperature Score", calculateTemperatureScore(patient));

        int finalScore = 0;
        for (Integer score : individualScores.values()) {
            finalScore += score;
        }
        individualScores.put("Final Score", finalScore);
    }
    public int getPreviousScore(Patient patient) {
        return patient.individualScores.get("Final Score");
    }


    // Method to calculate the score for the air or oxygen property
    private static int calculateAirOrOxygenScore(Patient patient) {
        return patient.getAirOrOxygen().getRespValue();
    }

    // Method to calculate the score for the consciousness property
    private static int calculateConsciousnessScore(Patient patient) {
        return patient.getConsciousness().getConsciousValue();
    }

    // Method to calculate the score for the respiration range property
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

    // Method to calculate the score for the SpO2 property
    private static int calculateSpo2Score(Patient patient) {
        return switch (patient.getAirOrOxygen()) {
            case AIR -> calculateSpo2ScoreForAir(patient);
            case OXYGEN -> calculateSpo2ScoreForOxygen(patient);
        };
    }

    // Method to calculate the score for the SpO2 property when the patient is breathing air
    private static int calculateSpo2ScoreForAir(Patient patient) {
        int spo2Score;
        if (patient.spo2 > 100 || patient.spo2 < 0) {
            throw new IllegalArgumentException("SpO2 value is invalid");
        } else if (patient.spo2 >= 97) {
            spo2Score = 3;
        } else if (patient.spo2 >= 95) {
            spo2Score = 2;
        } else if (patient.spo2 == 94) {
            spo2Score = 1;
        } else if (patient.spo2 >= 88) {
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

    // Method to calculate the score for the SpO2 property when the patient is breathing oxygen
    private static int calculateSpo2ScoreForOxygen(Patient patient) {
        int spo2Score;
        if (patient.spo2 >= 97) {
            spo2Score = 3;
        } else if (patient.spo2 >= 95) {
            spo2Score = 2;
        } else if (patient.spo2 >= 93) {
            spo2Score = 1;
        } else if (patient.spo2 >= 88) {
            spo2Score = 0;
        } else if (patient.spo2 >= 86) {
            spo2Score = 0;
        } else if (patient.spo2 >= 84) {
            spo2Score = 2;
        } else {
            spo2Score = 3;
        }
        return spo2Score;
    }

    // Method to calculate the score for the temperature property
    private static int calculateTemperatureScore(Patient patient) {
        int temperatureScore;
        if (patient.temperature > 45 || patient.temperature < 0) {
            throw new IllegalArgumentException("Temperature value is invalid");
        } else if (patient.temperature >= 39.1) {
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

    // Method to return a comment based on the air or oxygen status of the patient
    public String AirOrOxygenComment() {
        if (airOrOxygen == AirOrOxygen.AIR) {
            return "The patient is breathing air, and does not require supplementary oxygen.";
        } else {
            return "The patient requires supplementary oxygen.";
        }
    }

    // Method to return a comment based on the consciousness status of the patient
    public String ConsciousnessComment() {
        if (consciousness == Consciousness.ALERT) {
            return "The patient is conscious.";
        } else {
            return "The patient is unconscious or confused.";
        }
    }

    // Method to return a comment based on the SpO2 status of the patient
    public String Spo2Comment(Patient patient) {
        return switch (patient.getAirOrOxygen()) {
            case AIR -> Spo2CommentForAir(patient);
            case OXYGEN -> Spo2CommentForOxygen(patient);
        };
    }

    // Method to return a comment based on the SpO2 status of the patient when they are breathing oxygen
    private String Spo2CommentForOxygen(Patient patient) {
        if (patient.spo2 >= 97) {
            return "As the patient is breathing oxygen, this is very elevated.";
        } else if (patient.spo2 >= 95) {
            return "As the patient is breathing oxygen, this is elevated.";
        } else if (patient.spo2 >= 93) {
            return "As the patient is breathing oxygen, this is slightly elevated.";
        } else if (patient.spo2 >= 88) {
            return "This is a normal range for patients breathing either air or oxygen.";
        } else if (patient.spo2 >= 86) {
            return "As the patient is breathing oxygen, this is slightly low.";
        } else if (patient.spo2 >= 84) {
            return "As the patient is breathing oxygen, this is low.";
        } else {
            return "As the patient is breathing oxygen, this is very low.";
        }
    }

    // Method to return a comment based on the SpO2 status of the patient when they are breathing air
    private String Spo2CommentForAir(Patient patient) {
        if (patient.spo2 >= 89) {
            return "This is a normal range for patients breathing air.";
        } else if (patient.spo2 >= 88) {
            return "This is a normal range for patients breathing either air or oxygen.";
        } else if (patient.spo2 >= 86) {
            return "The patient may be suffering from hypoxemia.";
        } else if (patient.spo2 >= 84) {
            return "The patient may be suffering from hypoxemia.";
        } else {
            return "The patient is at risk of cyanosis";
        }
    }

    // Method to return a string representation of the patient including their name, properties, scores and comments
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
        sb.append(String.format(format, "Air or Oxygen", airOrOxygen.getRespValue(), individualScores.get("Air or Oxygen Score"), AirOrOxygenComment()));
        sb.append(line);
        sb.append(String.format(format, "Consciousness", consciousness.getConsciousValue(), individualScores.get("Consciousness Score"), ConsciousnessComment()));
        sb.append(line);
        sb.append(String.format(format, "Respiration Range", respirationRange, individualScores.get("Respiration Range Score"), ""));
        sb.append(line);
        sb.append(String.format(format, "SpO2", spo2, individualScores.get("SpO2 Score"), Spo2Comment(this)));
        sb.append(line);
        sb.append(String.format(format, "Temperature", temperature, individualScores.get("Temperature Score"), " "));
        sb.append(line);
        sb.append("The patients final Medi score is ").append(individualScores.get("Final Score")).append("\n");
        //System.out.println("Debug: individualScores = " + individualScores);
        return sb.toString();
    }
}

