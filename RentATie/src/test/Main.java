package test;

import bdd.Request;
import login.Officer;
import login.Pilot;
import order.Flight;
import order.Pilote;
import order.TieFighter;

import static status.FighterStatus.Functional;
import static status.PilotStatus.Safe;

public class Main {

    public static void main(String[] args) throws Exception {


        Officer off1 = new Officer("Pseudo1","Azerty123", 20);
        Pilot pil1 = new Pilot("Pseudo2", "Azerty321", 21,"Nom2", 20);

        //off1.createUser(off1);
        //off1.createUser(pil1);

        //Request.logIn("Pseudo1", "Azerty123");
        //Request.logIn("Pseudo2", "Azerty123");
        //Request.logIn("Pseudo1", "Az123");

        //off1.modifyOfficerPassword(off1, "Azerty321");
        //Request.logIn("Pseudo1", "Azerty123");
        Request.logIn("Pseudo1", "Azerty321");



//        Off1.displayPilotTable(Pilote.id);
//
//        System.out.println("--------------------------");
//
//        Request.displayTieFighterTable(TieFighter.fighterID);
//
//        System.out.println("--------------------------");
//
//        Off1.displayFlightTable(Flight.flightID);
//
//        System.out.println("--------------------------");
//
//        //Off1.assignFlight("Jean95",2,4,"mission4","2022-05-01", "2022-06-16");
//        Off1.flightDone(3, Safe,Functional);
//
//        System.out.println("--------------------------");
//
//        Off1.displayPilotTable(Pilote.id);
//
//        System.out.println("--------------------------");
//
//        Request.displayTieFighterTable(TieFighter.fighterID);
//
//        System.out.println("--------------------------");
//
//        Off1.displayFlightTable(Flight.flightID);
//
//        System.out.println("--------------------------");



    }
}
