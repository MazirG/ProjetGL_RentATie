
//faire plusieurs display (selon l'id, le nom, l'age,...)

package bdd;

import bCrypt.*;
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
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.Properties;

import static status.FighterStatus.Destroyed;
import static status.PilotStatus.Dead;
import static status.UserPost.Pilot;

public class Request {

    //se conncter à la bdd
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

    // afficher les tables, passer en paramètre l'attribut selon lequel on trie les lignes

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

    //met à jour la flight duration

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

    //Créer un user
    //Si officier alors le user n'est pas ajouté à la table Pilote
    //si pilote alors le user est ajouté à la table Pilote

    public static boolean createUser(UserPost post, String name, String username, Integer age, String pwd) throws Exception{
        try{
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            // idem pour le username

            ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username='"+username+"'");

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("username deja existant.");
                return false;
            }

            // si les informations précédentes ne sont pas prises, on insert le nouveau user

            PreparedStatement pstmt1 = con.prepareStatement("INSERT INTO User (`username`, `post`)VALUES ('" + username + "', '" + post + "')");
            pstmt1.executeUpdate();

            // on récupère l'id créé par la bdd

            Statement stmt1 = con.createStatement();
            ResultSet rst1 = stmt1.executeQuery("SELECT id FROM User WHERE username='"+username+"'");
            rst1.next();
            int userID = rst1.getInt(1);

            // on stock le mot de passe crypté dans la bdd

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

    //Assigne un vol en prenant un pilote et un vaisseau

    public static boolean assignFlight(int pilotID, int fighterID, String mission, String sStartDate, String sEndRent) throws Exception{
        try{
            LocalDate startDate = LocalDate.parse(sStartDate);
            LocalDate endRent = LocalDate.parse(sEndRent);

            Connection con = getConnection();

            //on vérifie la disponibilité du vaisseau

            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT inFlight,Status FROM TieFighter WHERE fighterID="+fighterID);

            rst.next();
            if (!rst.getString("status").equals("Functional") || rst.getInt("inFlight")!=0){
            System.out.println("Tie fighter not available");
            return false;
            }

            //on verifie la disponibilité du pilote

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


            // si les informations précédentes sont ok, on insert le nouveau vol

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

    public static boolean deleteUser(int id){
        try{
            Connection con = getConnection();

            //on vérifie que le pilote n'est pas en vol

            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT inFlight FROM Pilote WHERE id="+id);
            rst.next();

            if (rst.getBoolean(1)==false){
            PreparedStatement pstmt1 = con.prepareStatement("DELETE FROM User WHERE id="+id);
            pstmt1.executeUpdate();
            return true;
            } else {
                System.out.println("Le pilote est en vol");
                return false;}

        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("Delete Completed.");
        }
    }

    // modifie l'âge du pilote

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

    //modifier le mdp d'un user

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

    // on retourne l'username associé à l'id

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

    //Changer le statut des vaisseaux et des pilotes librement
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


    //méthode qu'on va appeler dans flightDone
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

    //affiche tous les vols d'un pilote passé en paramètre

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

    // verification du mot de passe
    public static int logIn(String username, String pwd) throws Exception{
        try{
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            // On vérifie que le username existe dans la bdd

            ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username='"+username+"'");
            rst.next();

            if (rst.getInt(1)>0){

                // On vérifie que le password entré correspond à celui enregistré pour cet username

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

    // On renvoie le post du username
    public static UserPost post(String username) throws Exception{
        try{
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            // On vérifie que le username existe dans la bdd
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


    //liste des pilotes et vaisseaux disponibles
    public static ObservableList displayPilotAvailable(){

        ObservableList<Pilot> pilots = FXCollections.observableArrayList();

        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM Pilote WHERE inFlight=FALSE AND status='Safe'");

            //on affiche les lignes

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

