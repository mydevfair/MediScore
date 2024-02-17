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

    public Patient(String name, AirOrOxygen airOrOxygen, Consciousness consciousness,
                   Integer respirationRange, Integer spo2, float temperature) {
        this.name = name;
        this.airOrOxygen = airOrOxygen;
        this.consciousness = consciousness;
        this.respirationRange = respirationRange;
        this.spo2 = spo2;
        this.temperature = temperature;
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
        this.respirationRange = respirationRate;}

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

    public Map<String, Integer> mediScoreFunction(Patient a) throws IllegalArgumentException {
        Map<String, Integer> individualScores = new HashMap<>();
        int finalScore = 0;

        int airOrOxygenScore = a.airOrOxygen.getRespValue();
        individualScores.put("Air or Oxygen", airOrOxygenScore);
        finalScore += airOrOxygenScore;

        int consciousnessScore = a.consciousness.getConsciousValue();
        individualScores.put("Consciousness", consciousnessScore);
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
        individualScores.put("Respiration Range", respirationRateScore);
        finalScore += respirationRateScore;

        int spo2Score;
        if(a.spo2 >= 97 )


        @Override
        public String toString () {
            String format = "| %-17s | %-11s |\n";
            String line = "+-------------------+-------------+\n";
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("Name: ").append(name).append("\n");
            sb.append(line);
            sb.append(String.format(format, "Property", "Observation"));
            sb.append(line);
            sb.append(String.format(format, "Air or Oxygen", airOrOxygen.getRespValue()));
            sb.append(line);
            sb.append(String.format(format, "Consciousness", consciousness.getConsciousValue()));
            sb.append(line);
            sb.append(String.format(format, "Respiration Range", respirationRange));
            sb.append(line);
            sb.append(String.format(format, "SpO2", spo2));
            sb.append(line);
            sb.append(String.format(format, "Temperature", temperature));
            sb.append(line);
            return sb.toString();
        }
    }
}
