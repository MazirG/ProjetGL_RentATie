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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RequestTest {

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

    @AfterEach
    void afterAll() throws Exception {

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

        rst = stmt.executeQuery("SELECT Max(id) FROM User");
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

    @Test
    void getConnectionTest() throws Exception {
        Connection verif = getConnection();
        assertNotNull(verif);
    }

    @Test
    void displayPilotTableTest() {
        ObservableList pilots = displayPilotTable();
        assertFalse(pilots.isEmpty());
    }

    @Test
    void displayTieFighterTableTest() {
        ObservableList fighters = displayTieFighterTable();
        assertFalse(fighters.isEmpty());
    }

    @Test
    void displayFlightTableTest() {
        ObservableList flights = displayFlightTable();
        assertFalse(flights.isEmpty());
    }

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

    @Test
    void createUserTestWrongUsername() throws Exception {

        boolean verif = Request.createUser(UserPost.Pilot,"pilotTest", "pilotTestUsername", 300, "pilotTestPwd" );
        assertFalse(verif);

    }

    @Test
    void createUserTestGoodUsername() throws Exception {
        Connection con = getConnection();
        assertNotNull(con);

        boolean verif = Request.createUser(UserPost.Pilot,"pilotTest2", "pilotTestUsername2", 300, "pilotTestPwd" );

        PreparedStatement pstmt = con.prepareStatement("DELETE FROM Pilote WHERE name='pilotTest2' ");
        pstmt.executeUpdate();

        pstmt = con.prepareStatement("DELETE FROM User WHERE username='pilotTestUsername2' ");
        pstmt.executeUpdate();

        con.close();
        assertTrue(verif);
    }

    @Test
    void createTieFighterTest() throws Exception {
        boolean verif = Request.createTieFighter(TieModel.Reaper);

        Connection con = getConnection();
        assertNotNull(con);

        Statement stmt = con.createStatement();
        ResultSet rst = stmt.executeQuery("SELECT Max(fighterID) FROM TieFighter");
        rst.next();

        int id =rst.getInt(1);

        PreparedStatement pstmt = con.prepareStatement("DELETE FROM TieFighter WHERE fighterID='"+id+"' ");
        pstmt.executeUpdate();

        con.close();
        assertTrue(verif);
    }

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

    @Test
    void assignFlightTestOK() throws Exception {

        boolean verif = assignFlight(999,9999,"missionTest2","2000-01-01","2023-01-01" );

        assertTrue(verif);

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("DELETE From Flight WHERE missionName='missionTest2'");
        pstmt.executeUpdate();

        con.close();

    }

    @Test
    void inFlightUpdateTest() throws Exception {
        boolean verif = InFlightUpdate(9999,999);
        assertTrue(verif);

    }

    @Test
    void deleteUserTestOK() throws Exception {

        boolean verif = deleteUser(999);
        assertTrue(verif);

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

    @Test
    void deleteUserTestNotOKPilotInFlight() throws Exception {

        Connection con = getConnection();
        assertNotNull(con);

        PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET inFlight=1 WHERE id=999 ");
        pstmt.executeUpdate();

        boolean verif = deleteUser(999);

        con.close();

        assertFalse(verif);
    }

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


    @Test
    void usernameTest() {
        String username = Request.username(999);
        assertEquals("pilotTestUsername", username);
    }

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

    @Test
    void displayHistoryTest() {
        ObservableList history = displayHistory(999);
        assertFalse(history.isEmpty());
    }

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

    @Test
    void logInTestOK() throws Exception {
        int idVerif = Request.logIn("pilotTestUsername", "pilotTestPwd");
        assertEquals(999, idVerif);

    }

    @Test
    void logInTestNotOKWrongPwd() throws Exception {

        int idVerif = Request.logIn("pilotTestUsername", "aaa");
        assertEquals(-1, idVerif);

    }

    @Test
    void logInTestNotOKWrongUsername() throws Exception {
        int idVerif = Request.logIn("aaa", "pilotTestPwd2");
        assertEquals(-1, idVerif);

    }

    @Test
    void postTest() throws Exception {

        UserPost post = Request.post("pilotTestUsername");
        assertEquals(UserPost.Pilot, post);
    }

    @Test
    void displayPilotAvailableTest() {
        ObservableList pilots = displayPilotAvailable();
        assertFalse(pilots.isEmpty());
    }

    @Test
    void displayTieFighterAvailableTest() {
        ObservableList fighters = displayTieFighterAvailable();
        assertFalse(fighters.isEmpty());
    }

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
