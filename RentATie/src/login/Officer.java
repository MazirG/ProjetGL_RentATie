package login;

import bdd.Request;
import status.FighterStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

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
    public void displayTable() {
        Request.displayPilotTable();
    }
    public void createPilot() throws Exception {
        Request.createPilot();
    }
    public void deletePilot() {
        Request.deletePilot();
    }
    public void modifyPilotAge() {
        Request.modifyPilotAge();
    }
    public void modifyPilotPassword() {
        Request.modifyPilotPassword();
    }

    //méthode sur la table flight
    public void displayFlightTable(){
        Request.displayFlightTable();
    }

    public void assignFlight() throws Exception{
        Request.assignFlight();
    }

}
