package testJUnit;

import org.junit.jupiter.api.Test;
import status.*;
import fly.*;

import org.junit.jupiter.api.Assertions;

/**
 * Testing the class TieFighter
 */
public class TieFighterTest {

    TieFighter tieFighterTest = new TieFighter(7,TieModel.Reaper,true,FighterStatus.Functional);

    /**
     * Test getFighterId.
     * @test.result fighterId has the right result
     */
    @Test
    void getFighterIdTest() {
        int fighterId= tieFighterTest.getFighterId();
        Assertions.assertEquals(7,fighterId);
    }

    /**
     * Test setFighterId.
     * @test.result fighterId has been set properly with the right result
     */
    @Test
    void setFighterIdTest() {
        tieFighterTest.setFighterId(8);
        int fighterId= tieFighterTest.getFighterId();
        Assertions.assertEquals(8,fighterId);

    }

    /**
     * Test getModel.
     * @test.result tieModel has the right result
     */
    @Test
    void getModelTest() {
        TieModel tieModel = tieFighterTest.getModel();
        Assertions.assertEquals(TieModel.Reaper, tieModel);

    }

    /**
     * Test setModel.
     * @test.result tieModel has been set properly with the right result
     */
    @Test
    void setModelTest() {
        tieFighterTest.setModel(TieModel.Fighter);
        TieModel tieModel = tieFighterTest.getModel();
        Assertions.assertEquals(TieModel.Fighter,tieModel);
    }

    /**
     * Test isInFlight.
     * @test.result inFlight has the right result
     */
    @Test
    void isInFlightTest() {
        boolean inFlight = tieFighterTest.isInFlight();
        Assertions.assertEquals(true,inFlight);

    }

    /**
     * Test setInFlight.
     * @test.result inFlight has been set properly with the right result
     */
    @Test
    void setInFlightTest() {
        tieFighterTest.setInFlight(false);
        boolean inFlight = tieFighterTest.isInFlight();
        Assertions.assertEquals(false,inFlight);
    }

    /**
     * Test getStatus.
     * @test.result fighterStatus has the right result
     */
    @Test
    void getStatusTest() {
        FighterStatus fighterStatus = tieFighterTest.getStatus();
        Assertions.assertEquals(FighterStatus.Functional,fighterStatus);

    }

    /**
     * Test setStatus.
     * @test.result fighterStatus has been set properly with the right result
     */
    @Test
    void setStatusTest() {
        tieFighterTest.setStatus(FighterStatus.Damaged);
        FighterStatus fighterStatus = tieFighterTest.getStatus();
        Assertions.assertEquals(FighterStatus.Damaged,fighterStatus);
    }
}

