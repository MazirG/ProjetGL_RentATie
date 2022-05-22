package login;

import status.PilotStatus;

/**
 * Represents a pilot
 */
public class Pilot extends User{
    private String name;
    private int age;
    boolean inFlight;
    private PilotStatus status;
    private int totalFlight;
    private int shipDestroyed;

    public Pilot(String username, String password, int pilotID, String name, int age, boolean inFlight, PilotStatus status, int totalFlight, int shipDestroyed) {
        super(pilotID,username, password);
        this.name = name;
        this.age = age;
        this.inFlight = inFlight;
        this.status = status;
        this.totalFlight = totalFlight;
        this.shipDestroyed = shipDestroyed;
    }

    public Pilot(String username, String password, int pilotID, String name, int age) {
        super(pilotID, username, password);
        this.name = name;
        this.age = age;
    }

    public Pilot(int pilotID, String name, int age,PilotStatus status, int totalFlight, int shipDestroyed, boolean inFlight) {
        super(pilotID,"","");
        this.name = name;
        this.age = age;
        this.inFlight = inFlight;
        this.status = status;
        this.totalFlight = totalFlight;
        this.shipDestroyed = shipDestroyed;
    }


    /**
     * @return current pilot name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return current pilot age
     */
    public int getAge() {
        return this.age;
    }

    /**
     * @return current pilot status
     */
    public PilotStatus getPilotStatus() {
        return this.status;
    }

    /**
     * @return current pilot total flight
     */
    public int getTotalFlight() {
        return this.totalFlight;
    }

    /**
     * @return current pilot number oh enemy's ship destroyed
     */
    public int getShipDestroyed() {
        return this.shipDestroyed;
    }

    /**
     * @return if the pilot is in flight
     */
    public boolean isInFlight() {
        return this.inFlight;
    }

    /**
     * @return current pilot id
     */
    public int getPilotID() {
        return super.getId();
    }

    /**
     * @return current pilot status
     */
    public PilotStatus getStatus() {
        return this.status;
    }

    /**
     * @return the current pilot id, his name, his age, if he is in flight, his status, his total flight, the number of enemy's ship he destroyed
     */
    @Override
    public String toString() {
        return "Pilot{" +
                "pilotID=" + super.getId()+
                ", name='" + name + '\'' +
                ", age=" + age +
                ", inFlight=" + inFlight +
                ", status=" + status +
                ", totalFlight=" + totalFlight +
                ", shipDestroyed=" + shipDestroyed +
                '}';
    }
}
