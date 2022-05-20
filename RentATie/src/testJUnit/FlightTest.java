package testJUnit;/*package testJUnit;

import fly.*;
import org.junit.jupiter.api.Assertions;

import static status.TieModel.*;

class FlightTest {

    Flight flightTest = new Flight(7,1,XWing,2,"Mission2","03/10/2021","15/10/2021","14/10/2021");

    @org.junit.jupiter.api.Test
    void getFlightIDTest() {
        int flightId= flightTest.getFlightID();
        Assertions.assertEquals(7,flightId);

    }

    @org.junit.jupiter.api.Test
    void setFlightIDTest() {
        flightTest.setFlightID(8);
        int flightId= flightTest.getFlightID();
        Assertions.assertEquals(8,flightId);

    }

    @org.junit.jupiter.api.Test
    void getMissionNameTest() {
        String missionName = flightTest.getMissionName();
        Assertions.assertEquals("Mission2",missionName);

    }

    @org.junit.jupiter.api.Test
    void setMissionNameTest() {
        flightTest.setMissionName("Mission3");
        String missionName = flightTest.getMissionName();
        Assertions.assertEquals("Mission3",missionName);

    }

    @org.junit.jupiter.api.Test
    void getStartTest() {
        String startFlight= flightTest.getStart();
        Assertions.assertEquals("03/10/2021",startFlight);

    }

    @org.junit.jupiter.api.Test
    void setStartTest() {
        flightTest.setStart("04/10/2021");
        String startFlight= flightTest.getStart();
        Assertions.assertEquals("04/10/2021",startFlight);
    }

    @org.junit.jupiter.api.Test
    void getEndRentTest() {
        String endRent= flightTest.getEndRent();
        Assertions.assertEquals("15/10/2021",endRent);
    }

    @org.junit.jupiter.api.Test
    void setEndRentTest() {
        flightTest.setEndRent("16/10/2021");
        String endRent= flightTest.getEndRent();
        Assertions.assertEquals("16/10/2021",endRent);
    }

    @org.junit.jupiter.api.Test
    void getEndFlightTest() {
        String endFlight= flightTest.getEndFlight();
        Assertions.assertEquals("14/10/2021",endFlight);
    }

    @org.junit.jupiter.api.Test
    void setEndFlightTest() {
        flightTest.setEndFlight("15/10/2021");
        String endFlight= flightTest.getEndFlight();
        Assertions.assertEquals("15/10/2021",endFlight);
    }

    @org.junit.jupiter.api.Test
    void getPilotIDTest() {
        int pilotID = flightTest.getPilotID();
        Assertions.assertEquals(1, pilotID);

    }

    @org.junit.jupiter.api.Test
    void getFighterIdTest() {
        int fighterId= flightTest.getFighterID();
        Assertions.assertEquals(2,fighterId);
    }

    @org.junit.jupiter.api.Test
    void setFighterIdTest() {
        flightTest.setFighterID(8);
        int fighterId= flightTest.getFighterID();
        Assertions.assertEquals(8,fighterId);

    }

}*/