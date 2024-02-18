import java.text.DecimalFormat;
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
        ALERT(0), CVPU1(1), CVPU2(2), CVPU3(3);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AirOrOxygen getAirOrOxygen() {
        return airOrOxygen;
    }

    public void setAirOrOxygen(AirOrOxygen airOrOxygen) {
        this.airOrOxygen = airOrOxygen;
    }

    public Consciousness getConsciousness() {
        return consciousness;
    }

    public void setConsciousness(Consciousness consciousness) {
        this.consciousness = consciousness;
    }

    public Integer getRespirationRange() {
        return respirationRange;
    }

    public void setRespirationRate(Integer respirationRate) {
        this.respirationRange = respirationRate;
    }

    public Integer getSpo2() {
        return spo2;
    }

    public void setSpo2(Integer spo2) {
        this.spo2 = spo2;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void mediScoreFunction(Patient a) throws IllegalArgumentException {

        int finalScore = 0;

        int airOrOxygenScore = airOrOxygen.getRespValue();
        individualScores.put("Air or Oxygen Score", airOrOxygenScore);
        finalScore += airOrOxygenScore;

        int consciousnessScore = consciousness.getConsciousValue();
        individualScores.put("Consciousness Score", consciousnessScore);
        finalScore += consciousnessScore;

        int respirationRateScore;
        if (a.respirationRange >= 25) {
            respirationRateScore = 3;
        } else if (a.respirationRange >= 21) {
            respirationRateScore = 2;
        } else if (a.respirationRange >= 12) {
            respirationRateScore = 0;
        } else if (a.respirationRange >= 9) {
            respirationRateScore = 1;
        } else {
            respirationRateScore = 3;
        }
        individualScores.put("Respiration Range Score", respirationRateScore);
        finalScore += respirationRateScore;

        int spo2Score;
        switch (a.airOrOxygen) {
            case OXYGEN -> {
                if (a.spo2 >= 97) {
                    spo2Score = 3;
                } else if (a.spo2 >= 95) {
                    spo2Score = 2;
                } else if (a.spo2 >= 93) {
                    spo2Score = 1;
                } else if (a.spo2 >= 88) {
                    spo2Score = 0;
                } else if (a.spo2 >= 86) {
                    spo2Score = 1;
                } else if (a.spo2 >= 84) {
                    spo2Score = 2;
                } else {
                    spo2Score = 3;
                }
                individualScores.put("SpO2 Score", spo2Score);
                finalScore += spo2Score;
            }
            case AIR -> {
                if (a.spo2 >= 93) {
                    spo2Score = 0;
                } else if (a.spo2 >= 86) {
                    spo2Score = 1;
                } else if (a.spo2 >= 84) {
                    spo2Score = 2;
                } else {
                    spo2Score = 3;
                }
                individualScores.put("SpO2 Score", spo2Score);
                finalScore += spo2Score;
            }
        }
        int temperatureScore;
        if (a.temperature >= 39.1) {
            temperatureScore = 2;
        } else if (a.temperature >= 38.1) {
            temperatureScore = 1;
        } else if (a.temperature >= 36.1) {
            temperatureScore = 0;
        } else if (a.temperature >= 35.1) {
            temperatureScore = 1;
        } else {
            temperatureScore = 3;
        }
        individualScores.put("Temperature Score", temperatureScore);
        finalScore += temperatureScore;


        individualScores.put("Final Score", finalScore);


    }

    public String AirOrOxygenComment(Patient patient) {
        if (airOrOxygen == AirOrOxygen.AIR) {
            return "The patient is breathing air, and does not require supplementary oxygen.";
        } else {
            return "The patient requires supplementary oxygen.";
        }
    }

    public String ConsciousnessComment(Patient patient) {
        if (consciousness == Consciousness.ALERT) {
            return "The patient is alert";
        } else if (consciousness == Consciousness.CVPU1) {
            return "The patient is unconscious or confused severity 1.";
        } else if (consciousness == Consciousness.CVPU2) {
            return "The patient is unconscious or confused severity 2.";
        } else {
            return "The patient is unconscious or confused severity 3.";
        }
    }

    public String Spo2Comment(Patient patient) {
        if (airOrOxygen == AirOrOxygen.OXYGEN) {
            if (spo2 >= 97) {
                return "As the patient is breathing oxygen, this is very elevated.";
            } else if (spo2 >= 95) {
                return "As the patient is breathing oxygen, this is quite elevated.";
            } else if (spo2 >= 93) {
                return "As the patient is breathing oxygen, this is elevated.";
            } else if (spo2 >= 88) {
                return "This is a normal range for patients breathing either air or oxygen.";
            } else if (spo2 >= 86) {
                return "As the patient is breathing oxygen, this is low.";
            } else if (spo2 >= 84) {
                return "As the patient is breathing oxygen, this is very low.";
            } else {
                return "The patient's SpO2 is dangerously low";
            }
        } else {
            if (spo2 >= 97) {
                return "The patient's SpO2 is within the normal range";
            } else if (spo2 >= 93) {
                return "As the patient is breathing air, this is a normal range.";
            } else if (spo2 >= 88) {
                return "This is a normal range for patients breathing either air or oxygen.";
            } else if (spo2 >= 86) {
                return "The patient's SpO2 is low";
            } else if (spo2 >= 84) {
                return "The patient's SpO2 is very low";
            } else {
                return "The patient's SpO2 is dangerously low";
            }
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
            sb.append(String.format(format, "Air or Oxygen", airOrOxygen.getRespValue(), individualScores.get("Air or Oxygen Score"), AirOrOxygenComment(this)));
            sb.append(line);
            sb.append(String.format(format, "Consciousness", consciousness.getConsciousValue(), individualScores.get("Consciousness Score"), ConsciousnessComment(this)));
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

