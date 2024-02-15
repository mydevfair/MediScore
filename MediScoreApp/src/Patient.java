public class Patient {
    public enum Resp {
        AIR(0), OXYGEN(2);
        private final int respValue;
        Resp(int respValue) {
            this.respValue = respValue;
        }
    }
    public enum Consciousness {
        ALERT(0), CVPU(3);
        private final int consciousValue;
        Consciousness(int consciousValue) {
            this.consciousValue = consciousValue;
        }
    }
    private final String name;
    private Resp resp;
    private Consciousness consciousness;
    public Patient(String name, Resp resp, Consciousness consciousness) {
        this.name = name;
        this.resp = resp;
        this.consciousness = consciousness;
    }

    public String getName() {
        return name;
    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public Consciousness getConsciousness() {
        return consciousness;
    }

    public void setConsciousness(Consciousness consciousness) {
        this.consciousness = consciousness;
    }
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", resp=" + resp +
                ", consciousness=" + consciousness +
                '}';
    }
}
