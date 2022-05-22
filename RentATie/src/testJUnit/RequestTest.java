package testJUnit;

import bCrypt.BCrypt;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import bdd.*;
import status.FighterStatus;
import status.PilotStatus;
import status.TieModel;
import status.UserPost;

import java.sql.*;
import java.time.LocalDate;

import static bdd.Request.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the class Request
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestTest {

    /**
     * This method inserts some data in the database that will be used in the tests.
     * This method is run before each test.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @BeforeEach
     void setUp() throws Exception {

        boolean verif = Request.createUser(UserPost.Pilot,"pilotTest", "pilotTestUsername", 300, "pilotTestPwd" );
        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET id=999 WHERE name='pilotTest'");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("UPDATE User SET id=999 WHERE username='pilotTestUsername'");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("INSERT INTO TieFighter(`model`, `inFlight`, `status`) VALUES ('" +TieModel.Fighter+ "', '" +0+ "', '" + FighterStatus.Functional + "')");
        pstmt.executeUpdate();

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT Max(fighterID) FROM TieFighter");
        rst.next();

        int maxFiID = rst.getInt(1);
        pstmt = con.prepareStatement("UPDATE TieFighter SET fighterID=9999 WHERE fighterID='"+maxFiID+ "' ");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("INSERT INTO Flight( `pilotID`, `fighterModel`, `fighterID`, `missionName`, `start`, `endRent`) VALUES ( 999, '" + TieModel.Fighter + "', 9999, 'MissionTest', '2020-01-01', '2023-01-01')");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("UPDATE Flight SET flightID=99999 WHERE fighterID='"+9999+ "' ");
        pstmt.executeUpdate();

        con.close();

    }

    /**
     * This method deletes the data that were added in the setUp() method from the database and reset the "Auto increment" parameters in the database to the right value.
     * This method is run after each test.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @AfterEach
    void tearDown() throws Exception {

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("DELETE FROM Pilote WHERE id="+999+" ");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("DELETE FROM User WHERE id="+999+" ");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("DELETE FROM TieFighter WHERE fighterID="+9999+" ");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("DELETE FROM Flight WHERE flightID="+99999+" ");
        pstmt.executeUpdate();

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT Max(fighterID) FROM TieFighter");
        rst.next();

        int maxFiID = rst.getInt(1);
        pstmt = con.prepareStatement("ALTER TABLE TieFighter AUTO_INCREMENT="+ (maxFiID+1) + " ");
        pstmt.executeUpdate();

        rst = stmt.executeQuery("SELECT Max(id) FROM Pilote");
        rst.next();

        int maxUserID = rst.getInt(1);
        pstmt = con.prepareStatement("ALTER TABLE User AUTO_INCREMENT="+ (maxUserID+1) + " ");
        pstmt.executeUpdate();

        rst = stmt.executeQuery("SELECT Max(flightID) FROM Flight");
        rst.next();

        int maxFlID = rst.getInt(1);
        pstmt = con.prepareStatement("ALTER TABLE Flight AUTO_INCREMENT="+ (maxFlID+1) + " ");
        pstmt.executeUpdate();

        con.close();

    }

    /**
     * Test getConnection.
     * @test.result Connection to the database.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void getConnectionTest() throws Exception {
        Connection verif = getConnection();
        verif.close();
        assertNotNull(verif);
    }

    /**
     * Test displayPilotTable.
     * @test.result The list returned is not empty.
     */
    @Test
    void displayPilotTableTest() {
        ObservableList pilots = displayPilotTable();
        assertFalse(pilots.isEmpty());
    }

    /**
     * Test displayTieFighterTable.
     * @test.result The list returned is not empty.
     */
    @Test
    void displayTieFighterTableTest() {
        ObservableList fighters = displayTieFighterTable();
        assertFalse(fighters.isEmpty());
    }

    /**
     * Test displayFlightTable.
     * @test.result The list returned is not empty.
     */
    @Test
    void displayFlightTableTest() {
        ObservableList flights = displayFlightTable();
        assertFalse(flights.isEmpty());
    }

    /**
     * Test updateFlightDuration.
     * @test.case Flight OK.
     * @test.result The method updateFlightDuration was executed correctly and the flight duration was changed to the correct value.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void updateFlightDurationTestOK() throws Exception {

        boolean verif = Request.updateFlightDuration(99999);
        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT flightDuration FROM Flight WHERE flightID=" + 99999);
        rst.next();

        int flightDuration = rst.getInt(1);

        rst = stmt.executeQuery("SELECT start FROM Flight WHERE flightID='"+99999+"'");
        rst.next();

        Date startDate = rst.getDate(1);
        Date date_of_today = Date.valueOf(LocalDate.now());

        long difference_In_Time = date_of_today.getTime() - startDate.getTime();

        int expectedFlightDuration = (int) ((difference_In_Time / (1000 * 60 * 60 * 24)) % 365);

        con.close();

        assertEquals(expectedFlightDuration, flightDuration);

    }

    /**
     * Test updateFlightDuration.
     * @test.case Flight already ended.
     * @test.result Flight duration not updated.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void updateFlightDurationTestNotOKEndedFlight() throws Exception {

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("UPDATE Flight SET endFlight= '"+ Date.valueOf("2023-05-05") +"' WHERE flightID=" + 99999);
        pstmt.executeUpdate();

        boolean verif = Request.updateFlightDuration(99999);

        con.close();

        assertFalse(verif);
    }

    /**
     * Test createUser.
     * @test.case Username already in the database.
     * @test.result User not created.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void createUserTestWrongUsername() throws Exception {

        boolean verif = Request.createUser(UserPost.Pilot,"pilotTest", "pilotTestUsername", 300, "pilotTestPwd" );
        assertFalse(verif);

    }

    /**
     * Test createUser.
     * @test.case New username.
     * @test.result User created.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void createUserTestGoodUsername() throws Exception {
        Connection con = getConnection();
        assertNotNull(con);

        boolean verif = Request.createUser(UserPost.Pilot,"pilotTest2", "pilotTestUsername2", 300, "pilotTestPwd" );

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username= 'pilotTestUsername2' ");
        rst.next();
        int inUser = rst.getInt(1);

        rst = stmt.executeQuery("SELECT COUNT(*) FROM Pilote WHERE name= 'pilotTest2' ");
        rst.next();
        int inPilot = rst.getInt(1);

        assertEquals(1,inUser);
        assertEquals(1,inPilot);

        PreparedStatement pstmt = con.prepareStatement("DELETE FROM Pilote WHERE name='pilotTest2' ");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("DELETE FROM User WHERE username='pilotTestUsername2' ");
        pstmt.executeUpdate();

        con.close();

        assertTrue(verif);
    }

    /**
     * Test createTieFighter.
     * @test.result TieFighter created.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void createTieFighterTest() throws Exception {

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM TieFighter");
        rst.next();
        int beforeCreation = rst.getInt(1);

        boolean verif = Request.createTieFighter(TieModel.Reaper);

        rst = stmt.executeQuery("SELECT COUNT(*) FROM TieFighter");
        rst.next();
        int afterCreation = rst.getInt(1);

        assertTrue(beforeCreation < afterCreation);

        rst = stmt.executeQuery("SELECT Max(fighterID) FROM TieFighter");
        rst.next();

        int id =rst.getInt(1);

        PreparedStatement pstmt = con.prepareStatement("DELETE FROM TieFighter WHERE fighterID='"+id+"' ");
        pstmt.executeUpdate();

        con.close();
        assertTrue(verif);
    }

    /**
     * Test assignFlight.
     * @test.case Pilot already in flight.
     * @test.result Flight not created.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void assignFlightTestNotOkPilotInFlight() throws Exception {
        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET inFlight=1 WHERE id=999 ");
        pstmt.executeUpdate();

        boolean verif = assignFlight(999,9999,"missionTest2","2000-01-01","2023-01-01" );

        con.close();
        assertFalse(verif);
    }

    /**
     * Test assignFlight.
     * @test.case Pilot not safe.
     * @test.result Flight not created.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void assignFlightTestNotOkPilotNotSafe() throws Exception {
        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET status='Lost' WHERE id=999 ");
        pstmt.executeUpdate();

        boolean verif = assignFlight(999,9999,"missionTest2","2000-01-01","2023-01-01" );

        con.close();
        assertFalse(verif);
    }

    /**
     * Test assignFlight.
     * @test.case TieFighter already in flight.
     * @test.result Flight not created.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void assignFlightTestNotOKFighterInFlight() throws Exception {

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("UPDATE TieFighter SET inFlight=1 WHERE fighterID=9999 ");
        pstmt.executeUpdate();

        boolean verif = assignFlight(999,9999,"missionTest2","2000-01-01","2023-01-01" );

        con.close();
        assertFalse(verif);
    }

    /**
     * Test assignFlight.
     * @test.case TieFighter not functional.
     * @test.result Flight not created.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void assignFlightTestNotOKFighterDestroyed() throws Exception {

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("UPDATE TieFighter SET status='Destroyed' WHERE fighterID=9999 ");
        pstmt.executeUpdate();

        boolean verif = assignFlight(999,9999,"missionTest2","2000-01-01","2023-01-01" );

        con.close();
        assertFalse(verif);
    }

    /**
     * Test assignFlight.
     * @test.case Pilot and TieFighter OK.
     * @test.result The method assignFlight was executed correctly and a flight was created.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void assignFlightTestOK() throws Exception {

        boolean verif = assignFlight(999,9999,"missionTest2","2000-01-01","2023-01-01" );

        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM Flight WHERE missionName='missionTest2' AND fighterID=9999 ");
        rst.next();
        int inFlight = rst.getInt(1);

        assertEquals(1, inFlight);

        PreparedStatement pstmt = con.prepareStatement("DELETE From Flight WHERE missionName='missionTest2' AND fighterID=9999 ");
        pstmt.executeUpdate();

        con.close();

    }

    /**
     * Test inFlightUpdate.
     * @test.result The method inFlightUpdate was executed correctly and the "inFlight" fields was changed to the correct value.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void inFlightUpdateTest() throws Exception {
        boolean verif = InFlightUpdate(9999,999);
        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT  inflight FROM Pilote WHERE id=999");
        rst.next();
        int inFlightPilot = rst.getInt(1);

        rst = stmt.executeQuery("SELECT  inflight FROM TieFighter WHERE fighterID=9999");
        rst.next();
        int inFlightFighter = rst.getInt(1);

        con.close();

        assertEquals(1, inFlightPilot);
        assertEquals(1, inFlightFighter);

    }

    /**
     * Test deleteUser.
     * @test.case Pilot OK.
     * @test.result The method deleteUser was executed correctly and the user was removed from the database.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void deleteUserTestOK() throws Exception {

        int verif = deleteUser(999);
        assertEquals(1,verif);

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("DELETE FROM Pilote WHERE name='pilotTest' ");
        pstmt.executeUpdate();

        boolean verif2 = Request.createUser(UserPost.Pilot,"pilotTest", "pilotTestUsername", 300, "pilotTestPwd" );
        assertTrue(verif2);

        pstmt = con.prepareStatement("UPDATE Pilote SET id="+999+" WHERE name='pilotTest'");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("UPDATE User SET id="+999+" WHERE username='pilotTestUsername'");
        pstmt.executeUpdate();

        con.close();


    }

    /**
     * Test deleteUser.
     * @test.case Pilot in flight.
     * @test.result Pilot not removed from the database and deleteUser returned -1.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void deleteUserTestNotOKPilotInFlight() throws Exception {

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET inFlight=1 WHERE id=999 ");
        pstmt.executeUpdate();

        int verif = deleteUser(999);

        con.close();

        assertEquals(0, verif);
    }

    /**
     * Test deleteUser.
     * @test.case User not in the database.
     * @test.result deleteUser returned -1.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void deleteUserTestNotOKUserNotInDatabase() throws Exception {

        int verif = deleteUser(99999999);
        assertEquals(0, verif);
    }

    /**
     * Test modifyPilotAge.
     * @test.result The method modifyPilotAge was executed correctly and the age was changed to the correct value.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void modifyPilotAgeTest() throws Exception {

        boolean verif = modifyPilotAge(999, 102);
        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT age FROM Pilote WHERE id ='"+ 999 +"'");
        rst.next();

        int age =rst.getInt(1);

        assertEquals(102, age);

        con.close();
    }


    /**
     * Test username (method that returns the username associated to an id).
     * @test.result The method username was executed correctly and returned the right username.
     */
    @Test
    void usernameTest() {
        String username = Request.username(999);
        assertEquals("pilotTestUsername", username);
    }

    /**
     * Test modifyFighterStatus.
     * @test.result The method modifyFighterStatus was executed correctly and the TieFighter status was changed to the correct value.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void modifyFighterStatusTest() throws Exception {
        boolean verif = modifyFighterStatus(9999, FighterStatus.Damaged);
        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT status FROM TieFighter WHERE fighterID ='"+ 9999 +"'");
        rst.next();

        String status =rst.getString(1);

        con.close();
        assertEquals(FighterStatus.Damaged, FighterStatus.valueOf(status));
    }

    /**
     * Test modifyPilotStatus.
     * @test.result The method modifyPilotStatus was executed correctly and the Pilot status was changed to the correct value.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void modifyPilotStatusTest() throws Exception {
        boolean verif = modifyPilotStatus(999, PilotStatus.Dead);
        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT status FROM Pilote WHERE id ='"+ 999 +"'");
        rst.next();

        String status =rst.getString(1);

        con.close();
        assertEquals(PilotStatus.Dead, PilotStatus.valueOf(status));
    }

    /**
     * Test doneFighterStatus.
     * @test.result The method doneFighterStatus was executed correctly, the TieFighter status and its "inFlight" field were changed to the correct value.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void doneFighterStatusTest() throws Exception {
        boolean verif = DoneFighterStatus(9999, FighterStatus.Damaged);
        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT status FROM TieFighter WHERE fighterID ='"+ 9999 +"'");
        rst.next();

        String status =rst.getString(1);

        rst = stmt.executeQuery("SELECT inFlight FROM TieFighter WHERE fighterID ='"+ 9999 +"'");
        rst.next();

        int inFlight =rst.getInt(1);

        con.close();
        assertEquals(FighterStatus.Damaged, FighterStatus.Damaged.valueOf(status));
        assertEquals(0,inFlight);
    }

    /**
     * Test donePilotStatus.
     * @test.result The method donePilotStatus was executed correctly, the Pilot status and its "inFlight" field were changed to the correct value.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void donePilotStatusTest() throws Exception {

        boolean verif = DonePilotStatus(999, PilotStatus.Dead);
        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT status FROM Pilote WHERE id ='"+ 999 +"'");
        rst.next();

        String status =rst.getString(1);

        rst = stmt.executeQuery("SELECT inFlight FROM Pilote WHERE id ='"+ 999 +"'");
        rst.next();

        int inFlight =rst.getInt(1);

        con.close();
        assertEquals(PilotStatus.Dead, PilotStatus.valueOf(status));
        assertEquals(0,inFlight);
    }

    /**
     * Test displayHistory.
     * @test.result The list returned is not empty.
     */
    @Test
    void displayHistoryTest() {
        ObservableList history = displayHistory(999);
        assertFalse(history.isEmpty());
    }

    /**
     * Test flightDone.
     * @test.case Flight OK.
     * @test.result The method flightDone was executed correctly and the "shipDestroyed" field associated to the pilot was changed to the correct value.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void flightDoneTestOK() throws Exception {

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT shipDestroyed FROM Pilote WHERE id ='"+ 999 +"'");
        rst.next();

        int shipDestroyedSaved = rst.getInt(1);
        int shipDestroyedExpected = (shipDestroyedSaved + 5 );

        con.close();

        boolean verif = flightDone(99999, PilotStatus.Safe, FighterStatus.Functional, 5);
        assertTrue(verif);

        Connection con2 = getConnection();
        assertNotNull(con2);

        Statement stmt2 = con2.createStatement();
        ResultSet rst2 = stmt2.executeQuery("SELECT shipDestroyed FROM Pilote WHERE id ='"+ 999 +"'");
        rst2.next();

        int shipDestroyedSaved2 = rst2.getInt(1);

        con2.close();

        assertEquals(shipDestroyedExpected, shipDestroyedSaved2);
    }

    /**
     * Test flightDone.
     * @test.case Flight already ended.
     * @test.result Flight is not changed.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void flightDoneTestNotOkFlightEnded() throws Exception {

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("UPDATE Flight SET endFlight='2023-01-01' WHERE flightID=" +99999 );
        pstmt.executeUpdate();

        con.close();

        boolean verif = flightDone(99999, PilotStatus.Safe, FighterStatus.Functional, 5);
        assertFalse(verif);

    }

    /**
     * Test logIn
     * @test.case Good username and password.
     * @test.result The method logIn was executed correctly and returned the right id.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void logInTestOK() throws Exception {
        int idVerif = Request.logIn("pilotTestUsername", "pilotTestPwd");
        assertEquals(999, idVerif);

    }

    /**
     * Test logIn
     * @test.case Wrong password.
     * @test.result The method logIn was executed correctly and returned -1 (user not logged in).
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void logInTestNotOKWrongPwd() throws Exception {

        int idVerif = Request.logIn("pilotTestUsername", "aaa");
        assertEquals(-1, idVerif);

    }

    /**
     * Test logIn
     * @test.case Wrong username.
     * @test.result The method logIn was executed correctly and returned -1 (user not logged in).
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void logInTestNotOKWrongUsername() throws Exception {
        int idVerif = Request.logIn("aaa", "pilotTestPwd2");
        assertEquals(-1, idVerif);

    }

    /**
     * Test post (method that returns the post associated to a username).
     * @test.result The method post was executed correctly and returned the right post.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void postTest() throws Exception {

        UserPost post = Request.post("pilotTestUsername");
        assertEquals(UserPost.Pilot, post);
    }

    /**
     * Test displayPilotAvailable.
     * @test.result The list returned is not empty.
     */
    @Test
    void displayPilotAvailableTest() {
        ObservableList pilots = displayPilotAvailable();
        assertFalse(pilots.isEmpty());
    }

    /**
     * Test displayTieFighterAvailable.
     * @test.result The list returned is not empty.
     */
    @Test
    void displayTieFighterAvailableTest() {
        ObservableList fighters = displayTieFighterAvailable();
        assertFalse(fighters.isEmpty());
    }

    /**
     * Test modifyUserPassword
     * @test.result The method modifyUserPassword was executed correctly, the new "hashed" and "salt" fields saved in the database are the right ones.
     * @throws Exception If there was a problem in the execution of the methods used.
     */
    @Test
    void modifyUserPasswordTest() throws Exception {

        boolean verif = modifyUserPassword(999, "pilotTestPwd2");
        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT salt FROM User WHERE id='" + 999 + "'");
        rst.next();

        String savedSalt = rst.getString(1);
        String testedHashed = BCrypt.hashpw("pilotTestPwd2", savedSalt);

        rst = stmt.executeQuery("SELECT hashed FROM User WHERE id='" + 999 + "'");
        rst.next();

        String savedHashed = rst.getString(1);

        assertEquals(testedHashed, savedHashed);
    }
}
