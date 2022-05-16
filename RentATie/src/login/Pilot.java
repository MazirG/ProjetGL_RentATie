package login;

import bdd.Request;
import status.PilotStatus;

public class Pilot {
    private int pilotID;
    private String name;
    private int age;
    boolean inFlight;
    private PilotStatus status;
    private int totalFlight;
    private int shipDestroyed;

    public Pilot(int pilotID,String name, int age,boolean inFlight,PilotStatus status,int totalFlight,int shipDestroyed) {
        this.pilotID=pilotID;
        this.name=name;
        this.age=age;
        this.inFlight=inFlight;
        this.status=status;
        this.totalFlight=totalFlight;
        this.shipDestroyed=shipDestroyed;
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
        Request.displayHistory(this.pilotID);
    }


}
