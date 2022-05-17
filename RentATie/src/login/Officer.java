package login;

import bdd.Request;
import order.Flight;
import order.Pilote;
import status.FighterStatus;
import status.PilotStatus;
import status.UserPost;


public class Officer extends User{

    private int officerID ;

    public Officer(String username, String password, int officerID) {
        super(username, password);
        this.officerID = officerID;
    }

    public int getOfficerID() {
        return officerID;
    }


    //méthode sur la table pilot
    public void displayPilotTable(Pilote order) {
        Request.displayPilotTable(order);
    }

    public void createUser(Pilot pilot) throws Exception {
        Request.createUser(pilot.getPilotID(), UserPost.Pilot, pilot.getName(), pilot.getUsername(), pilot.getAge(), pilot.getSalt(), pilot.getHashed());
    }

    public void createUser(Officer officer) throws Exception {
        Request.createUser(officer.getOfficerID(), UserPost.Officer, null, officer.getUsername(),null, officer.getSalt(), officer.getHashed());
    }

    public void deletePilot(int id) {
        Request.deletePilot(id);
    }
    public void modifyPilotAge(int id, int Age) {
        Request.modifyPilotAge(id, Age);
    }

    public void modifyPilotPassword(Pilot pilot , String newPwd) {
        pilot.setPassword(newPwd);
        Request.modifyUserPassword(pilot.getPilotID(), pilot.getSalt(), pilot.getHashed());
    }

    public void modifyOfficerPassword(Officer officer , String newPwd) {
        officer.setPassword(newPwd);
        Request.modifyUserPassword(officer.getOfficerID(), officer.getSalt(), officer.getHashed());
    }

    //méthode sur la table flight
    public void displayFlightTable(Flight order){
        Request.displayFlightTable(order);
    }

    public void assignFlight(String pilotUsername, int fighterID, int flightID, String mission, String startDate, String endRent) throws Exception{
        Request.assignFlight(pilotUsername,fighterID,flightID,mission, startDate,endRent);
    }

    public void flightDone(int id, PilotStatus Pstatus, FighterStatus Fstatus){
        Request.flightDone(id, Pstatus, Fstatus);}

    public void modifyPilotStatus(int id, PilotStatus status){
        Request.modifyPilotStatus(id,status);
    }

    public void modifyFighterStatus(int id, FighterStatus Status){
        Request.modifyFighterStatus(id,Status);
    }

}
