package testJUnit;

import login.*;
import org.junit.jupiter.api.Test;
import status.*;

import org.junit.jupiter.api.Assertions;

import java.util.Observable;

/**
 * Testing the class Pilot
 */
public class PilotTest {
    Pilot pilotTest = new Pilot("username", "password", 7, "luffy", 23, false, PilotStatus.Safe, 10, 2);

    /**
     * Test getName.
     * @test.result namePilot has the right result
     */
    @Test
     void getNameTest() {
        String namePilot = pilotTest.getName();
        Assertions.assertEquals("luffy", namePilot);
    }

    /**
     * Test getAge.
     * @test.result agePilot has the right result
     */
    @Test
    void getAgeTest() {
        int agePilot = pilotTest.getAge();
        Assertions.assertEquals(23, agePilot);
    }

    /**
     * Test getPilotStatus.
     * @test.result statusPilot has the right result
     */
    @Test
    void getPilotStatusTest() {
        PilotStatus statusPilot = pilotTest.getPilotStatus();
        Assertions.assertEquals(PilotStatus.Safe, statusPilot);
    }

    /**
     * Test getTotalFlight.
     * @test.result totFlightPilot has the right result
     */
    @Test
    void getTotalFlightTest() {
        int totFlightPilot = pilotTest.getTotalFlight();
        Assertions.assertEquals(10, totFlightPilot);
    }

    /**
     * Test getShipDestroyed.
     * @test.result totShipDestroyedPilot has the right result
     */
    @Test
    void getShipDestroyedTest() {
        int totShipDestroyedPilot = pilotTest.getShipDestroyed();
        Assertions.assertEquals(2, totShipDestroyedPilot);
    }

    /**
     * Test isInFlight.
     * @test.result isInFlightPilot has the right result
     */
    @Test
    void isInFlightTest() {
        boolean isInFlightPilot = pilotTest.isInFlight();
        Assertions.assertEquals(false, isInFlightPilot);
    }

    /**
     * Test getPilotID.
     * @test.result iDPilot has the right result
     */
    @Test
    void getPilotIDTest() {
        int iDPilot = pilotTest.getPilotID();
        Assertions.assertEquals(7, iDPilot);
    }

    /**
     * Test getStatus.
     * @test.result statusPilot has the right result
     */
    @Test
    void getStatusTest() {
        PilotStatus statusPilot = pilotTest.getPilotStatus();
        Assertions.assertEquals(PilotStatus.Safe, statusPilot);
    }
}
