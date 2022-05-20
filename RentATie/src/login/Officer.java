package login;

import bdd.Request;
import fly.*;
import status.FighterStatus;
import status.PilotStatus;
import status.UserPost;


public class Officer extends User{

    private int officerID;

    public Officer(int id, String username, String password) {
        super(id, username, password);
        this.officerID = id;
    }

    public int getOfficerID() {
        return officerID;
    }

   /* //méthodes sur les tables pilot et user
    public void displayPilotTable() {
        Request.displayPilotTable();
    }

  *//*  public void createUser(Pilot pilot) throws Exception {
        Request.createUser(UserPost.Pilot, pilot.getName(), pilot.getUsername(), pilot.getAge(), pilot.getSalt(), pilot.getHashed());
    }*//*

    public void deletePilot(User user) {

        Request.deleteUser(user.getId());
    }
    public void modifyPilotAge(Pilot pilot, int newAge) {
        Request.modifyPilotAge(pilot.getPilotID(), newAge);
    }


    //méthode sur la table flight
    public void displayFlightTable(){
        Request.displayFlightTable();
    }

    public void assignFlight(Pilot pilot, TieFighter tieFighter, Flight flight) throws Exception{
        Request.assignFlight(pilot.getPilotID(),tieFighter.getFighterId(),flight.getMissionName(), flight.getStart(),flight.getEndRent());
    }

    public void flightDone(Flight flight, PilotStatus Pstatus, FighterStatus Fstatus){
        Request.flightDone(flight.getFlightID(), Pstatus, Fstatus);}

    public void modifyPilotStatus(Pilot pilot, PilotStatus newStatus){
        Request.modifyPilotStatus(pilot.getPilotID(), newStatus);
    }

    public void modifyFighterStatus(TieFighter tieFighter, FighterStatus newStatus){
        Request.modifyFighterStatus(tieFighter.getFighterId(), newStatus);
    }

    //méthodes sur la table tieFighter

    public void displayTieFighterTable(){
        Request.displayTieFighterTable();
    }
    */

}
