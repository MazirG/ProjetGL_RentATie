package testJUnit;

import bdd.Request;
import login.Officer;
import org.junit.jupiter.api.Assertions;

class OfficerTest {

    @org.junit.jupiter.api.Test
    void getOfficerIDTest() {
        Officer officer= new Officer("officer","password",1);
        int officerId= officer.getOfficerID();

        Assertions.assertEquals(1,officerId);
    }

    @org.junit.jupiter.api.Test
    void displayTableTest() {
        boolean verif= Request.displayPilotTable();
        Assertions.assertEquals(true,verif);
    }

    @org.junit.jupiter.api.Test
    void createPilotTest() {
        boolean verif= false;
        try {
            verif = Request.createPilot();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(true,verif);
    }

    @org.junit.jupiter.api.Test
    void deletePilotTest() {
        boolean verif= Request.deletePilot();
        Assertions.assertEquals(true,verif);
    }

    @org.junit.jupiter.api.Test
    void modifyPilotAgeTest() {
        boolean verif= Request.modifyPilotAge();
        Assertions.assertEquals(true,verif);
    }

    @org.junit.jupiter.api.Test
    void modifyPilotPassword() {
        boolean verif= Request.modifyPilotPassword();
        Assertions.assertEquals(true,verif);
    }

    @org.junit.jupiter.api.Test
    void displayFlightTableTest() {
        boolean verif= Request.displayFlightTable();
        Assertions.assertEquals(true,verif);
    }

    @org.junit.jupiter.api.Test
    void assignFlightTest() {
        boolean verif= false;
        try {
            verif = Request.assignFlight();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(true,verif);
    }
}