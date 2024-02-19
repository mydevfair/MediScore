import java.util.HashMap;
import java.util.Map;

public class Patient {
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

    private String name;
    private AirOrOxygen airOrOxygen;
    private Consciousness consciousness;

    private Integer respirationRange;
    private Integer spo2;
    private float temperature;
    Map<String, Integer> individualScores = new HashMap<>();

    public Patient(String name, AirOrOxygen airOrOxygen, Consciousness consciousness,
                   Integer respirationRange, Integer spo2, float temperature) {
        this.name = name;
        this.airOrOxygen = airOrOxygen;
        this.consciousness = consciousness;
        this.respirationRange = respirationRange;
        this.spo2 = spo2;
        this.temperature = Math.round(temperature * 10) / 10.0f;
    }

    public AirOrOxygen getAirOrOxygen() {
        return airOrOxygen;
    }

    public Consciousness getConsciousness() {
        return consciousness;
    }

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

    private static int calculateAirOrOxygenScore(Patient patient) {
        return patient.getAirOrOxygen().getRespValue();
    }

    private static int calculateConsciousnessScore(Patient patient) {
        return patient.getConsciousness().getConsciousValue();
    }

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

    private static int calculateSpo2Score(Patient patient) {
        return switch (patient.getAirOrOxygen()) {
            case AIR -> calculateSpo2ScoreForAir(patient);
            case OXYGEN -> calculateSpo2ScoreForOxygen(patient);
        };
    }

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

    private static int calculateTemperatureScore(Patient patient) {
        int temperatureScore;
        if (patient.temperature >= 37.5) {
            temperatureScore = 2;
        } else if (patient.temperature >= 37.1) {
            temperatureScore = 1;
        } else {
            temperatureScore = 0;
        }
        return temperatureScore;
    }


    public String AirOrOxygenComment() {
        if (airOrOxygen == AirOrOxygen.AIR) {
            return "The patient is breathing air, and does not require supplementary oxygen.";
        } else {
            return "The patient requires supplementary oxygen.";
        }
    }

    public String ConsciousnessComment() {
        if (consciousness == Consciousness.ALERT) {
            return "The patient is conscious";
        } else {
            return "The patient is unconscious or confused.";
        }
    }

    public String Spo2Comment(Patient patient) {
        return switch (patient.getAirOrOxygen()) {
            case AIR -> Spo2CommentForAir();
            case OXYGEN -> Spo2CommentForOxygen();
        };
    }

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


        @Override
        public String toString () {
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

