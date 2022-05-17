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
        super(username, password);
        this.pilotID = pilotID;
        this.name = name;
        this.age = age;
        this.inFlight = inFlight;
        this.status = status;
        this.totalFlight = totalFlight;
        this.shipDestroyed = shipDestroyed;
    }

    public Pilot(String username, String password, int pilotID, String name, int age) {
        super(username, password);
        this.pilotID = pilotID;
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public PilotStatus getStatusPilot() {
        return status;
    }

    public int getTotalFlight() {
        return totalFlight;
    }

    public int getShipDestroyed() {
        return shipDestroyed;
    }

    public boolean isInFlight() {
        return inFlight;
    }

    public int getPilotID() {
        return pilotID;
    }

    public PilotStatus getStatus() {
        return status;
    }

    public void displayHistory() {
        Request.displayHistory(this.username);
    }


}
