
//faire plusieurs display (selon l'id, le nom, l'age,...)

package bdd;

import bCrypt.*;
import controllers.Controller;
import fly.Flight;
import fly.TieFighter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import login.Pilot;
import login.User;
import status.FighterStatus;
import status.PilotStatus;
import status.TieModel;
import status.UserPost;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

import static status.UserPost.Pilot;

/**
 * Contains all the methods that make requests to the database
 */
public class Request{

    /**
     * This method allows to connect to de data base
     * @return
     * @throws Exception if the connection has not been established
     */

    public static Connection getConnection() throws Exception{
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("RentATie/ressources/conf.properties")) {
            props.load(fis);
            String driver = props.getProperty("jdbc.driver.class");
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return conn;
        } catch(Exception e){System.out.println(e);}
        return null;
    }

    /**
     * this method display the pilot table
     * @return an Observable List with all the pilots of the data base, this type makes easier displaying the pilots in the view
     */
    public static ObservableList displayPilotTable(){

        ObservableList<Pilot> pilots = FXCollections.observableArrayList();

        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM Pilote ");

            //on affiche les lignes

            while (rst.next()) {
                String statut =rst.getString(4) ;
                PilotStatus ps = PilotStatus.valueOf(statut);
                Pilot pilot = new Pilot(rst.getInt(1), rst.getString(2), rst.getInt(3), ps, rst.getInt(5), rst.getInt(6), rst.getBoolean(7));
                pilots.add(pilot);
            }
            con.close();

            return pilots;

        } catch (Exception e) {
            e.printStackTrace();
            return pilots;
        }
    }

    /**
     * this method display the tie table
     * @return an Observable List with all the ties of the data base, this type makes easier displaying the ties in the view
     */
    public static ObservableList displayTieFighterTable(){

        ObservableList<TieFighter> fighters = FXCollections.observableArrayList();

        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM TieFighter");

            //on affiche les lignes

            while (rst.next()) {
                String model = rst.getString(2);
                TieModel tm = TieModel.valueOf(model);
                String statut = rst.getString(4);
                FighterStatus fs = FighterStatus.valueOf(statut);
                TieFighter tiefighter = new TieFighter(rst.getInt(1), tm, rst.getBoolean(3), fs);
                fighters.add(tiefighter);
            }

            con.close();

            return fighters;

        } catch (Exception e) {
            e.printStackTrace();
            return fighters;
        }
    }

    /**
     * this method display the flight table
     * @return an Observable List with all the flights of the data base, this type makes easier displaying the flights in the view
     */
    public static ObservableList displayFlightTable(){

        ObservableList<Flight> flights = FXCollections.observableArrayList();
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT flightID FROM Flight WHERE endFlight IS NULL");

            while (rst.next()) {
                int flightID = rst.getInt(1);
                updateFlightDuration(flightID);
            }

            rst = stmt.executeQuery("SELECT * FROM Flight");

            while (rst.next()) {
                String model = rst.getString(3);
                TieModel tieModel = TieModel.valueOf(model);
                Flight flight = new Flight(rst.getInt(1), rst.getInt(2), tieModel, rst.getInt(4),rst.getString(5),rst.getString(6),rst.getString(7),rst.getString(8), rst.getInt(9));
                flights.add(flight);
            }

            con.close();

            return flights;

        } catch (Exception e) {
            e.printStackTrace();
            return flights;
        }
    }


    /**
     * this method updates the flight duration if the flight is in progress
     * @param flightID the flight we want to update the duration id
     * @return if the duration has been updated
     */
    public static boolean updateFlightDuration(int flightID){

        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT endFlight FROM Flight WHERE flightID='"+flightID+"'");
            rst.next();

            Date dateEndFlight = rst.getDate(1);

            if (dateEndFlight == null) {

                rst = stmt.executeQuery("SELECT start FROM Flight WHERE flightID='"+flightID+"'");
                rst.next();

                Date startDate = rst.getDate(1);
                Date date_of_today = Date.valueOf(LocalDate.now());

                long difference_In_Time = date_of_today.getTime() - startDate.getTime();

                int flightDuration = (int) ((difference_In_Time / (1000 * 60 * 60 * 24)) % 365);

                if (flightDuration < 0){

                    PreparedStatement pstmt = con.prepareStatement("UPDATE Flight SET flightDuration='"+0+"' WHERE flightID="+flightID);
                    pstmt.executeUpdate();

                } else {

                    PreparedStatement pstmt2 = con.prepareStatement("UPDATE Flight SET flightDuration='"+flightDuration+"' WHERE flightID="+flightID);
                    pstmt2.executeUpdate();

                }

                System.out.println("Flight duration mise à jour.");
                con.close();

                return true;

            }
            else{
                System.out.println("Le vol est terminé.");
                con.close();

                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * this methods is used for the registration of a user, the id is auto incremented. It checks if the new user username is not already taken,
     * if it is there is no registration.
     * @param post the new user post
     * @param name the new user name
     * @param username the new user username which must be unique
     * @param age the new user age
     * @param pwd the new user password which well be hashed in the bdd table
     * @return if the registration has been completed
     * @throws Exception if the user has not been registered
     */
    public static boolean createUser(UserPost post, String name, String username, Integer age, String pwd) throws Exception{
        try{
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username='"+username+"'");

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("username deja existant.");
                return false;
            }

            PreparedStatement pstmt1 = con.prepareStatement("INSERT INTO User (`username`, `post`)VALUES ('" + username + "', '" + post + "')");
            pstmt1.executeUpdate();

            Statement stmt1 = con.createStatement();
            ResultSet rst1 = stmt1.executeQuery("SELECT id FROM User WHERE username='"+username+"'");
            rst1.next();
            int userID = rst1.getInt(1);

            User user = new User(userID,username, pwd);
            String salt = user.getSalt();
            String hashed = user.getHashed();

            PreparedStatement pstmt2 = con.prepareStatement("UPDATE User SET salt='"+salt+"', hashed='"+hashed+"' WHERE id="+userID);
            pstmt2.executeUpdate();

            if (post == Pilot) {
                PreparedStatement pstmt3 = con.prepareStatement("INSERT INTO Pilote VALUES ('"+userID+"', '"+name+"', '"+age+"', 'safe', 0, 0, 0)");
                pstmt3.executeUpdate();
            }

            con.close();
            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("Create Completed.");
        }
    }

    /**
     * this methods register a tie. Its id is auto incremented, it is initially functional and not in flight.
     * @param model the model of the tie we are registering
     * @return if the registration has been completed
     * @throws Exception if the tie has not been registered
     */
    public static boolean createTieFighter(TieModel model ) throws Exception{
        try{
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO TieFighter (`model`, `inFlight`, `status`) VALUES ('"+model+"', '0', 'functional')");
            pstmt.executeUpdate();

            con.close();
            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("Create Completed.");
        }
    }

    /**
     * this method assign a pilot to a tie for a flight. the flight id is auto incremented. The end of the flight is not
     * informed but there is the date of the end of the tie rent. The method checks if the pilot and the te are available
     * and fit to fly. It also checks if the id corresponds to a pilot and not to an officer.
     * @param pilotID id of the pilot assigned to the flight
     * @param fighterID  id of the tie used for the flight
     * @param mission the name of the flight mission
     * @param sStartDate the start date of the flight
     * @param sEndRent the end date of the tie rent
     * @return if the assignment has been registered
     * @throws Exception if the assignment has not been registered
     */
    public static boolean assignFlight(int pilotID, int fighterID, String mission, String sStartDate, String sEndRent) throws Exception{
        try{
            LocalDate startDate = LocalDate.parse(sStartDate);
            LocalDate endRent = LocalDate.parse(sEndRent);

            Connection con = getConnection();

            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT inFlight,Status FROM TieFighter WHERE fighterID="+fighterID);

            rst.next();
            if (!rst.getString("status").equals("Functional") || rst.getInt("inFlight")!=0){
            System.out.println("Tie fighter not available");
            return false;
            }

            Statement stmt3 = con.createStatement();
            ResultSet rst3 = stmt3.executeQuery("SELECT COUNT(id) FROM Pilote WHERE id="+pilotID);
            rst3.next();

            Statement stmt1 = con.createStatement();
            ResultSet rst1 = stmt1.executeQuery("SELECT inFlight,Status FROM Pilote WHERE id="+pilotID);

            if (rst3.getInt(1)==0){
                System.out.println("Le user correspondant n'est pas un pilote");
                return false;
            }else {
                rst1.next();
                if (!rst1.getString("status").equals("Safe") || rst1.getInt("inFlight") != 0) {
                    System.out.println("Pilot not available");
                    return false;
                }
            }

            ResultSet rst2 = stmt.executeQuery("SELECT model FROM TieFighter WHERE fighterID="+fighterID);
            rst2.next();
            String model = rst2.getString(1);

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Flight (`pilotID`, `fighterModel`, `fighterID`, `missionName`, `start`, `endRent`) VALUES ('"+pilotID+"', '"+model+"', '"+fighterID+"', '"+mission+"', '"+startDate+"', '"+endRent+"')");
            pstmt.executeUpdate();

            con.close();

            InFlightUpdate(fighterID, pilotID);

            return true;

        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("Assignment Completed.");
        }
    }

    //quand on assigne un vol, le vaisseau est directement inFlight=1=true

    /**
     * this method is called in assignFlight. It updates automatically the pilot and tie availability indicating they are in flight.
     * @param fighterID the id of the tie used for the flight
     * @param pilotID the id of the user assigned to the flight
     * @return if the updates has been done
     */
    public static boolean InFlightUpdate(int fighterID, int pilotID){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE TieFighter SET inFlight="+1+" WHERE fighterID="+fighterID);
            pstmt.executeUpdate();

            PreparedStatement pstmt1 = con.prepareStatement("UPDATE Pilote SET inFlight="+1+" WHERE id="+pilotID);
            pstmt1.executeUpdate();

            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("UpdateInFlight Completed.");
        }    }

    /**
     * this methods delete a user from the user table. But if the user was a pilot, his profile is not deleted from the pilot table thus his records still accessible.
     * Deleted from the user table, he can't access to the application.
     * @param id of the user who will be deleted from the user table
     * @return if the removal has been done
     */
    public static int deleteUser(int id){
        try{
            Connection con = getConnection();

            //on vérifie que l'utilisateur est bien dans la bdd

            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE id="+id);
            rst.next();

            int isInUser = rst.getInt(1);

            if (isInUser == 1){

                String username = Request.username(id);
                UserPost post = Request.post(username);

                if (post.equals(Pilot)){

                    //si c'est un pilote on vérifie qu'il n'est pas en vol

                    rst = stmt.executeQuery("SELECT inFlight FROM Pilote WHERE id="+id);
                    rst.next();

                    if (rst.getBoolean(1)==false){
                        PreparedStatement pstmt1 = con.prepareStatement("DELETE FROM User WHERE id="+id);
                        pstmt1.executeUpdate();
                        return 1;
                    } else {
                        System.out.println("Le pilote est en vol");
                        return 0;
                    }
                }
                else {
                    PreparedStatement pstmt1 = con.prepareStatement("DELETE FROM User WHERE id="+id);
                    pstmt1.executeUpdate();
                    return 1;
                }
            }
            else {
                System.out.println("User pas dans la bdd");
                return 0;
            }

        } catch(Exception e){
            System.out.println(e);
            return -1;
        }
        finally {
            System.out.println("Delete Completed.");
        }
    }


    /**
     * this method modify the age of a pilot
     * @param id the id of the pilot concerned
     * @param Age the new age of the pilot
     * @return if the update has been completed
     */
    public static boolean modifyPilotAge(int id, int Age){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET age="+Age+" WHERE id="+id);
            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            System.out.println("Modify completed.");
        }
    }

    /**
     * this method modify the password of a user. The password is hashed.
     * @param id the id of the user changing his password
     * @param newPwd the user new password
     * @return if the update has been completed
     */
    public static boolean modifyUserPassword(int id, String newPwd){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();

            ResultSet rst = stmt.executeQuery("SELECT username FROM User WHERE id='"+id+"'");
            rst.next();

            String username = rst.getString(1);

            User user = new User(id, username, newPwd);

            String newSalt = user.getSalt();
            String newHashed = user.getHashed();

            PreparedStatement pstmt = con.prepareStatement("UPDATE User SET salt='"+newSalt+"', hashed='"+newHashed+"' WHERE id="+id);
            pstmt.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;

        } finally {
            System.out.println("Modify completed.");
        }
    }

    /**
     * this method collects a pilot username from his id
     * @param id id of the pilot we are looking for
     * @return the username linked to the pilot id
     */
    public static String username(int id){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();

            ResultSet rst = stmt.executeQuery("SELECT username FROM User WHERE id='"+id+"'");
            rst.next();

            String username = rst.getString(1);

            return username;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * this method updates a tie status
     * @param id id of the tie concerned
     * @param Status the new tie status
     * @return if the update has been completed
     */
    public static boolean modifyFighterStatus(int id, FighterStatus Status){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE TieFighter SET status='"+Status+"' WHERE fighterID="+id);
            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            System.out.println("ModifyFighterStatus completed.");
        }
    }

    /**
     * this method updates the pilot status
     * @param id id of the pilot concerned
     * @param status the new pilot status
     * @return if the update has been completed
     */
    public static boolean modifyPilotStatus(int id, PilotStatus status){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET status='"+status+"' WHERE id="+id);
            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            System.out.println("ModifyPilotStatus completed.");
        }
    }


    /**
     * this method is called in the method flightDone(). It updates the tie status once the flight done.
     * @param id id of the tie used for the flight
     * @param Status new tie status once the flight done
     * @return if the update has been completed
     */
    public static boolean DoneFighterStatus(int id, FighterStatus Status){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE TieFighter SET status='"+Status+"' WHERE fighterID="+id);
            pstmt.executeUpdate();

            PreparedStatement pstmt1 = con.prepareStatement("UPDATE TieFighter SET inFlight=0 WHERE fighterID="+id);
            pstmt1.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            System.out.println("ModifyFighterStatus completed.");
        }
    }

    /**
     * this method is called in the method flightDone(). It updates the pilot status once the flight done.
     * @param id id of the pilot assigned for the flight
     * @param status new pilot status once the flight done
     * @return if the update has been completed
     */
    public static boolean DonePilotStatus(int id, PilotStatus status){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET status='"+status+"' WHERE id="+id);
            pstmt.executeUpdate();

            PreparedStatement pstmt1 = con.prepareStatement("UPDATE Pilote SET totalFlight=totalFlight+1 WHERE id="+id);
            pstmt1.executeUpdate();

            PreparedStatement pstmt2 = con.prepareStatement("UPDATE Pilote SET inFlight=0 WHERE id="+id);
            pstmt2.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            System.out.println("ModifyPilotStatus completed.");
        }
    }


    /**
     * this method display the flight history of a pilot
     * @param pilotID the id of the pilot we want to see his history
     * @return an Observable List with all the flights the pilot had done, this type makes easier displaying the history in the view
     */
    public static ObservableList displayHistory(int pilotID) {

        ObservableList<Flight> flights = FXCollections.observableArrayList();

        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT flightID FROM Flight WHERE endFlight IS NULL AND pilotID="+pilotID);

            while (rst.next()) {
                int flightID = rst.getInt(1);
                updateFlightDuration(flightID);
            }

            rst = stmt.executeQuery("SELECT * FROM Flight WHERE pilotID="+pilotID);

            while (rst.next()) {
                String model = rst.getString(3);
                TieModel tm = TieModel.valueOf(model);
                Flight flight = new Flight(rst.getInt(1), rst.getInt(2), tm, rst.getInt(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getInt(9));
                flights.add(flight);
            }
            con.close();

            return flights;

        }  catch (Exception e) {
            e.printStackTrace();
            return flights;
        }
    }

    //Vol terminé, on met à jour le statut du vaisseau et celui de pilote


    /**
     * this method update the pilot and tie status once the flight done. It also updates the number of enemies ships the pilot destroyed
     * @param id  id of the flight done
     * @param Pstatus new status of the pilot assigned to the flight
     * @param Fstatus new status of the tie used for the flight
     * @param shipDestroyed number of enemies ships the pilots destroyed
     * @return if the updates have been completed
     */
    public static boolean flightDone(int id, PilotStatus Pstatus, FighterStatus Fstatus, int shipDestroyed){

        //create an object for date
        LocalDate date_of_today = LocalDate.now();

        try{
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            ResultSet rst = stmt.executeQuery("SELECT endFlight FROM Flight WHERE flightID = "+id);
            rst.next();
            Date dateEndFlight = rst.getDate(1);

            if (dateEndFlight == null) {

                PreparedStatement pstmt = con.prepareStatement("UPDATE Flight SET endFlight='" + date_of_today + "' WHERE flightID=" + id);
                pstmt.executeUpdate();

                ResultSet rst1 = stmt.executeQuery("SELECT pilotID FROM Flight WHERE flightID ="+ id);
                rst1.next();
                int Pid = rst1.getInt(1);
                DonePilotStatus(Pid,Pstatus);

                ResultSet rst2 = stmt.executeQuery("SELECT fighterID FROM Flight WHERE flightID=" + id);
                rst2.next();
                int Fid = rst2.getInt(1);
                DoneFighterStatus(Fid, Fstatus);

                ResultSet rst5 = stmt.executeQuery("SELECT shipDestroyed FROM Pilote WHERE id=" + Pid);
                rst5.next();

                int shipDestroyedSaved = rst5.getInt(1);
                shipDestroyedSaved = (shipDestroyedSaved + shipDestroyed );

                PreparedStatement pstmt2 = con.prepareStatement("UPDATE Pilote SET shipDestroyed='"+shipDestroyedSaved+"' WHERE id="+Pid);
                pstmt2.executeUpdate();
                return true;

            } else {
                System.out.println("Flight already done");
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            System.out.println("Modify completed.");
        }
    }


    /**
     * this method allows the user to connect to the application thanks to his username and password. First the method checks if the username exists, then if the
     * password is the good one
     * @param username username of the user who wants to connect
     * @param pwd the password entered by the user
     * @return the id of the user if he succeed in connecting. Otherwise it returns -1
     * @throws Exception if there is an error in the authentication
     */
    public static int logIn(String username, String pwd) throws Exception{
        try{
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username='"+username+"'");
            rst.next();

            if (rst.getInt(1)>0){

                rst = stmt.executeQuery("SELECT salt FROM User WHERE username='"+username+"'");
                rst.next();

                String savedSalt = rst.getString(1);
                String testedHashed = BCrypt.hashpw(pwd, savedSalt);

                rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username='"+username+"' AND hashed='"+testedHashed+"'");
                rst.next();

                if (rst.getInt(1)>0) {

                    rst = stmt.executeQuery("SELECT id FROM User WHERE username='"+username+"'");
                    rst.next();

                    int id = rst.getInt(1);
                    System.out.println("Logged in.");

                    return(id);
                }
                else {
                    System.out.println("Wrong username or password");
                    return(-1);
                }
            } else{
                System.out.println("Wrong username or password");
                return(-1);
            }

        } catch(Exception e){
            System.out.println(e);
            return (-1);
        }
        finally {
            System.out.println("Create Completed.");
        }
    }


    /**
     * this method checks if the username of the user is in the user table and returns his post
     * @param username username of the user we are focusing on
     * @return the post of the user
     * @throws Exception if there is problem in the research
     */
    public static UserPost post(String username) throws Exception{
        try{
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username='"+username+"'");
            rst.next();

            if (rst.getInt(1)>0) {

                rst = stmt.executeQuery("SELECT post FROM User WHERE username='" + username + "'");
                rst.next();

                UserPost post;
                post = UserPost.valueOf(rst.getString(1));
                return post;
            }
            else {
                System.out.println("Wrong username");
                return null;
            }

        } catch(Exception e){
            System.out.println(e);
            return null;
        }
    }

    /**
     * this methods displays all the pilots who are able to take a flight.
     * @return an Observable List with all the pilots available to take a flight, this type makes easier displaying the pilots in the view
     */
    public static ObservableList displayPilotAvailable(){

        ObservableList<Pilot> pilots = FXCollections.observableArrayList();

        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM Pilote WHERE inFlight=FALSE AND status='Safe'");

            while (rst.next()) {
                String statut =rst.getString(4) ;
                PilotStatus ps = PilotStatus.valueOf(statut);
                Pilot pilot = new Pilot(rst.getInt(1), rst.getString(2), rst.getInt(3), ps, rst.getInt(5), rst.getInt(6), rst.getBoolean(7));
                pilots.add(pilot);
            }
            return pilots;

        } catch (Exception e) {
            e.printStackTrace();
            return pilots;
        }
    }

    /**
     * this methods displays all the ties which are able to fly.
     * @return an Observable List with all the ties able to fly, this type makes easier displaying the tie in the view
     */
    public static ObservableList displayTieFighterAvailable(){

        ObservableList<TieFighter> fighters = FXCollections.observableArrayList();

        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM TieFighter WHERE inFlight=FALSE AND status='Functional'");

            //on affiche les lignes

            while (rst.next()) {
                String model = rst.getString(2);
                TieModel tm = TieModel.valueOf(model);
                String statut = rst.getString(4);
                FighterStatus fs = FighterStatus.valueOf(statut);
                TieFighter tiefighter = new TieFighter(rst.getInt(1), tm, rst.getBoolean(3), fs);
                fighters.add(tiefighter);
            }
            return fighters;

        } catch (Exception e) {
            e.printStackTrace();
            return fighters;
        }
    }

}

