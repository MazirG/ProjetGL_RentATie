package login;

import bdd.Request;
import status.PilotStatus;

public class Pilot extends User{
    private int pilotID;
    private String name;
    private String username;
    private String password;
    private int age;
    boolean inFlight;
    private PilotStatus status;
    private int totalFlight;
    private int shipDestroyed;

    public Pilot(String username, String password, int pilotID, String name, int age, boolean inFlight, PilotStatus status, int totalFlight, int shipDestroyed) {
        super(pilotID,username, password);
        this.pilotID = pilotID;
        this.name = name;
        this.age = age;
        this.inFlight = inFlight;
        this.status = status;
        this.totalFlight = totalFlight;
        this.shipDestroyed = shipDestroyed;
    }

    public Pilot(String username, String password, int pilotID, String name, int age) {
        super(pilotID, username, password);
        this.pilotID = pilotID;
        this.name = name;
        this.age = age;
    }

    public Pilot(int pilotID, String name, int age,PilotStatus status, int totalFlight, int shipDestroyed, boolean inFlight) {
        super(pilotID,"","");
        this.pilotID = pilotID;
        this.name = name;
        this.age = age;
        this.inFlight = inFlight;
        this.status = status;
        this.totalFlight = totalFlight;
        this.shipDestroyed = shipDestroyed;
    }



    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public PilotStatus getPilotStatus() {
        return this.status;
    }

    public int getTotalFlight() {
        return this.totalFlight;
    }

    public int getShipDestroyed() {
        return this.shipDestroyed;
    }

    public boolean isInFlight() {
        return this.inFlight;
    }

    public int getPilotID() {
        return this.pilotID;
    }

    public PilotStatus getStatus() {
        return this.status;
    }

    /* public void displayHistory() {
        Request.displayHistory(this.pilotID);
    } */

    @Override
    public String toString() {
        return "Pilot{" +
                "pilotID=" + pilotID +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", inFlight=" + inFlight +
                ", status=" + status +
                ", totalFlight=" + totalFlight +
                ", shipDestroyed=" + shipDestroyed +
                '}';
    }
}
