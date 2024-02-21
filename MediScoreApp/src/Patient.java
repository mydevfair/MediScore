/*
 * ------------------------------------------------------------------------------
 *File: Patient.java
 * Author: Christopher Fairhurst
 * Date: 21st February 2024
 * Description: This class represents a patient undergoing medical assessment and facilitates
 * the calculation of their MediScore based on various vital signs.
 * ------------------------------------------------------------------------------
 * Key Features:
 * Stores patient attributes like respiration, oxygen saturation, and consciousness level.
 * Calculates a MediScore based on individual scores for each attribute.
 * Provides comments to explain the individual scores and overall MediScore.
 * Tracks changes in MediScore over time and alerts for significant increases.
 * Utilises enums (AirOrOxygen and Consciousness) to represent observation values with assigned scores.
 * ------------------------------------------------------------------------------
 * Functionality:
 * Update patient information with new vital signs.
 * Calculate the MediScore based on current observations.
 * Compare current MediScore to previous one and alert for significant changes.
 * Generate comments to explain individual scores and MediScore interpretation.
 * ------------------------------------------------------------------------------
 * Usage:
 * Create a Patient object with initial vital signs and fasting status.
 * Update patient information using the updatePatient method to insert new vital sign readings.
 * Call the calculateMediScore method to calculate the MediScore.
 * Check for increases in Mediscore using the scoreAlert method.
 * Access the individual scores and comments through respective methods.
 * ------------------------------------------------------------------------------
 */
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Patient {
    // Enumeration for AirOrOxygen with integer values assigned to them
    // Assigned values give the mediscore the individual scores for this
    // Section of the assessment
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

    //Enumeration for Consciousness with integer values assigned to them
    // Assigned values give the mediscore the individual scores for this
    // Section of the assessment
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
    private Integer respirationRange;
    private Integer spo2;
    private float temperature;
    private int airOrOxygenObs;
    private int consciousnessObs;
    private float CBG;
    private boolean fasting;
    private Timestamp timestamp;
    private Timestamp previousTimestamp;
    Map<String, Integer> individualScores = new HashMap<>();
    Map<String, Integer> previousMediScores = new HashMap<>();

    // Constructor for the Patient class
    // Creates an object of patient with the required statistics to assess the patients mediscore
    // Rounds the float values to one decimal place as requested
    // Also gives an enum(integer) value from int input for the observation value
    public Patient(String name, int airOrOxygenObs, int consciousnessObs, Integer respirationRange, Integer spo2, float temperature, float CBG, boolean fasting) {
        this.name = name;
        this.airOrOxygenObs = airOrOxygenObs;
        this.consciousnessObs = consciousnessObs;
        this.respirationRange = respirationRange;
        this.spo2 = spo2;
        this.temperature = Math.round(temperature * 10) / 10.0f; // Rounding the temperature to 1 decimal place
        this.CBG = Math.round(CBG * 10) / 10.0f;
        this.fasting = fasting;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // This is an update patient details method that allows the user to update the patient's details
    public void updatePatient(int airOrOxygenObs, int consciousnessObs, Integer respirationRange, Integer spo2, float temperature, float CBG, boolean fasting) {
        this.airOrOxygenObs = airOrOxygenObs;
        this.consciousnessObs = consciousnessObs;
        this.respirationRange = respirationRange;
        this.spo2 = spo2;
        this.temperature = Math.round(temperature * 10) / 10.0f; // Rounding the temperature to 1 decimal place
        this.CBG = Math.round(CBG * 10) / 10.0f;
        this.fasting = fasting;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // getters
    public String getName() {
        return name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Timestamp getPreviousTimestamp() {
        return previousTimestamp;
    }

    // Method to calculate the MediScore for the patient.
    // It gets individual scores from separate methods and then loops through and
    // adds them up to get the final score which is also then saved in the map.
    // It then prints the mediscore table checks for a raise in score and then adds the details to
    // The previous scores map for future comparison and then adds them up to get the final score.
    // It also stores the individual scores in a map.
    public void calculateMediScore(Patient patient) {
        clearMap(individualScores);// Clear the map to ensure it is empty before adding new scores
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
        printTable(patient); // Print the Mediscore table for the patient
        scoreAlert(patient); // Check if the patient's score has raised by more than 2 points
        addPreviousMediScore(patient); // Add the current score to the previous scores map
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

    // This is a child method that is called from the parents switch statement
    // Calculates the score for a patient that is breathing air and not on oxygen
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

    // This is a child method that is called from the parents switch statement
    // Calculates the score for a patient that is on oxygen supported breathing
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

    // This is the method to calculate CBG score
    // The architecture is the same as the air or oxygen method
    // Except it uses a boolean value in an if else statement to decide whether
    // the patient has eaten or is fasting
    // Boolean = True for fasting || False if not fasting
    private static int calculateCBGScore(Patient patient) {
        if (patient.fasting) {
            return calculateCBGFasting(patient);
        } else {
            return calculateCBGNotFasting(patient);
        }
    }

    // This is a support methods for CBG score that calculates the score for a patient that is fasting
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

    // This is a support methods for CBG score that calculates the score for a patient that is not fasting
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

    // Method to print mediscore table for the patient which is called from calculate mediscore method
    public void printTable(Patient patient) {
        System.out.println(patient);
    }

    // This method clears the hashmap to ensure the mediscore method had a clear map to input scores into
    public void clearMap(Map<String, Integer> map) {
        map.clear();
    }

    // This method first checks the previous scores hash map to check if the patient has a previous score
    // It then subtracts the previous score from the current score and checks to see if it has raised by more than 2 points
    // Within a 24-hour period of the previous score being taken
    // It then outputs the corresponding message to console
    // I know the messages should be returned and not printed straight from the method but time was
    // limited and I wanted to finish with a working version
    public void scoreAlert(Patient patient) {
        if (previousMediScores.isEmpty()) {
            System.out.println(patient.getName() + " Has no previous score to compare with.");
        } else {
            Timestamp previousTimestamp = patient.getPreviousTimestamp();
            Timestamp currentTimestamp = patient.getTimestamp();

            if (previousTimestamp == null) {
                System.out.println(patient.getName() + " Has no previous timestamp to compare with.");
            } else {
                long millisecondsInADay = 24 * 60 * 60 * 1000;
                long timeDifference = currentTimestamp.getTime() - previousTimestamp.getTime();
                boolean within24Hours = timeDifference <= millisecondsInADay;

                if (within24Hours) {
                    int previousScore = patient.previousMediScores.get("Final Score");
                    int newScore = patient.individualScores.get("Final Score");
                    int scoreDifference = newScore - previousScore;

                    if (scoreDifference > 2) {
                        System.out.println("ALERT!!!! " + patient.getName() + " MediScore has changed by more than 2 points in the last 24 hours.");
                    } else {
                        System.out.println(patient.getName() + " MediScore has not changed by more than 2 points in the last 24 hours.");
                    }
                } else {
                    System.out.println("Previous Mediscore was taken more than 24 hours ago.");
                }
            }
        }
    }

    // Method to add the previous MediScore for the patient
    // Stores previous Mediscore in a HashMap so that it can be used
    // when the score alert method is called and the current and previous
    // scores can be compared
    public void addPreviousMediScore(Patient patient) {
        int finalScore;
        name = patient.getName();
        finalScore = patient.individualScores.get("Final Score");
        previousMediScores.put("Final Score", finalScore);
        previousTimestamp = patient.getTimestamp();
    }

    // The following methods are used to generate comments for the patient's attributes
    // These methods are called in the override for the toString method of the Patient class
    // Comments are then inserted into the table to give a more detailed explanation of the patient's score

    public String AirOrOxygenComment() {
        if (airOrOxygenObs == 0) {
            return "The patient is breathing air, and does not require supplementary oxygen.";
        } else {
            return "The patient requires supplementary oxygen.";
        }
    }

    public String ConsciousnessComment() {
        if (consciousnessObs == 0) {
            return "The patient is conscious.";
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

    // The following method generates a comment for the CBG attribute giving different comments
    // Depending on whether the patient is fasting or not
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
        return "\n" +
                "Name: " + name + "\n" +
                line +
                String.format(format, "Property", "Observation", "Score", "Comment") +
                line +
                String.format(format, "Air or Oxygen", airOrOxygenObs, individualScores.get("Air or Oxygen Score"), AirOrOxygenComment()) +
                line +
                String.format(format, "Consciousness", consciousnessObs, individualScores.get("Consciousness Score"), ConsciousnessComment()) +
                line +
                String.format(format, "Respiration Range", respirationRange, individualScores.get("Respiration Range Score"), "") +
                line +
                String.format(format, "SpO2", spo2, individualScores.get("SpO2 Score"), Spo2Comment(this)) +
                line +
                String.format(format, "Temperature", temperature, individualScores.get("Temperature Score"), "") +
                line +
                String.format(format, "CBG", CBG, individualScores.get("CBG Score"), CBGComment()) +
                line +
                "The patient's final Medi score is " + individualScores.get("Final Score") + ".\n";
    }
}
