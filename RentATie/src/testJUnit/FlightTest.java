package testJUnit;

import fly.*;
import org.junit.jupiter.api.Assertions;
import status.TieModel;

/**
 * Testing the class Flight
 */
public class FlightTest {

    Flight flightTest = new Flight(7,10, TieModel.Reaper, 3,"Mission2","03/10/2021","15/10/2021","14/10/2021",0);

    /**
     * Test getFlightId.
     * @test.result FlightId has the right result
     */
    @org.junit.jupiter.api.Test
    void getFlightIDTest() {
        int flightId= flightTest.getFlightID();
        Assertions.assertEquals(7,flightId);

    }

    /**
     * Test setFlightID.
     * @test.result FlightId has been set properly with the right result
     */
    @org.junit.jupiter.api.Test
    void setFlightIDTest() {
        flightTest.setFlightID(8);
        int flightId= flightTest.getFlightID();
        Assertions.assertEquals(8,flightId);

    }

    /**
     * Test getMissionName.
     * @test.result missionName has the right result
     */
    @org.junit.jupiter.api.Test
    void getMissionNameTest() {
        String missionName = flightTest.getMissionName();
        Assertions.assertEquals("Mission2",missionName);

    }

    /**
     * Test setMissionName.
     * @test.result missionName has been set properly with the right result
     */
    @org.junit.jupiter.api.Test
    void setMissionNameTest() {
        flightTest.setMissionName("Mission3");
        String missionName = flightTest.getMissionName();
        Assertions.assertEquals("Mission3",missionName);

    }

    /**
     * Test getStart.
     * @test.result startFlight has the right result
     */
    @org.junit.jupiter.api.Test
    void getStartTest() {
        String startFlight= flightTest.getStart();
        Assertions.assertEquals("03/10/2021",startFlight);

    }

    /**
     * Test setStart.
     * @test.result startFlight has been set properly with the right result
     */
    @org.junit.jupiter.api.Test
    void setStartTest() {
        flightTest.setStart("04/10/2021");
        String startFlight= flightTest.getStart();
        Assertions.assertEquals("04/10/2021",startFlight);
    }

    /**
     * Test getEndRent.
     * @test.result endRent has the right result
     */
    @org.junit.jupiter.api.Test
    void getEndRentTest() {
        String endRent= flightTest.getEndRent();
        Assertions.assertEquals("15/10/2021",endRent);
    }

    /**
     * Test setEndRent.
     * @test.result endRent has been set properly with the right result
     */
    @org.junit.jupiter.api.Test
    void setEndRentTest() {
        flightTest.setEndRent("16/10/2021");
        String endRent= flightTest.getEndRent();
        Assertions.assertEquals("16/10/2021",endRent);
    }

    /**
     * Test getEndFlight.
     * @test.result endFlight has the right result
     */
    @org.junit.jupiter.api.Test
    void getEndFlightTest() {
        String endFlight= flightTest.getEndFlight();
        Assertions.assertEquals("14/10/2021",endFlight);
    }

    /**
     * Test setEndFlight.
     * @test.result endFlight has been set properly with the right result
     */
    @org.junit.jupiter.api.Test
    void setEndFlightTest() {
        flightTest.setEndFlight("15/10/2021");
        String endFlight= flightTest.getEndFlight();
        Assertions.assertEquals("15/10/2021",endFlight);
    }

    /**
     * Test getPilotID.
     * @test.result iDPilot has the right result
     */
    @org.junit.jupiter.api.Test
    void getPilotIDTest() {
        int iDPilot = flightTest.getPilotID();
        Assertions.assertEquals(10, iDPilot);

    }

    /**
     * Test getFighterId.
     * @test.result fighterId has the right result
     */
    @org.junit.jupiter.api.Test
    void getFighterIdTest() {
        int fighterId= flightTest.getFighterID();
        Assertions.assertEquals(3,fighterId);
    }

    /**
     * Test setFighterId.
     * @test.result fighterId has been set properly with the right result
     */
    @org.junit.jupiter.api.Test
    void setFighterIdTest() {
        flightTest.setFighterID(8);
        int fighterId= flightTest.getFighterID();
        Assertions.assertEquals(8,fighterId);

    }

    /**
     * Test getFlightDuration.
     * @test.result flightDuration has the right result
     */
    @org.junit.jupiter.api.Test
    void getFlightDurationTest() {
        int flightDuration= flightTest.getFlightDuration();
        Assertions.assertEquals(0,flightDuration);
    }

    /**
     * Test setFlightDuration.
     * @test.result flightDuration has been set properly with the right result
     */
    @org.junit.jupiter.api.Test
    void setFlightDurationTest() {
        flightTest.setFlightDuration(8);
        int flightDuration= flightTest.getFlightDuration();
        Assertions.assertEquals(8,flightDuration);

    }


}
