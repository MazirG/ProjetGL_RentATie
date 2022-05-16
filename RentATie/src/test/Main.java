package test;

import login.*;
import bdd.Request;

public class Main {

    public static void main(String[] args) throws Exception {


        Officer Off1 = new Officer("mr","tamboula", 17);

        Request.displayTieFighterTable();

        System.out.println("_____________");

        Request.displayPilotTable();

        System.out.println("_____________");

        Request.displayFlightTable();    }
}
