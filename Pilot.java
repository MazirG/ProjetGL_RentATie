package server;

import java.util.Set;

public class Pilot extends User{

    private int pilotID;
    private String pilotStatus;
    private int age;
    private Set<String> flightDone;
    private int totalFlight;
    private int shipDestroyed;

    public Pilot(String username, String password, int pilotID, String pilotStatus, int age, Set<String> flightDone, int totalFlight, int shipDestroyed) {
        super(username, password);
        this.pilotID = pilotID;
        this.pilotStatus = pilotStatus;
        this.age = age;
        this.flightDone = flightDone;
        this.totalFlight = totalFlight;
        this.shipDestroyed = shipDestroyed;
    }

    public int getPilotID() {
        return pilotID;
    }

    public String getPilotStatus() {
        return pilotStatus;
    }

    public int getAge() {
        return age;
    }

    public Set<String> getFlightDone() {
        return flightDone;
    }

    public int getTotalFlight() {
        return totalFlight;
    }

    public int getShipDestroyed() {
        return shipDestroyed;
    }

}
