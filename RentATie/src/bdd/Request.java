
//faire plusieurs display (selon l'id, le nom, l'age,...)

package bdd;

import BCrypt.BCrypt;
import order.Flight;
import order.Pilote;
import order.TieFighter;
import status.FighterStatus;
import status.PilotStatus;
import status.UserPost;

import java.io.FileInputStream;
import java.sql.*;
import java.time.LocalDate;
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

    public static boolean displayUserTable(){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM User");

            //on affiche les lignes

            while (rst.next())
                System.out.println(rst.getInt(1) + " "+ rst.getString(2) + " "+ rst.getString(3) + " "+ rst.getString(4));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // afficher les tables, passer en paramètre l'attribut selon lequel on trie les lignes

    public static boolean displayPilotTable(Pilote order){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM Pilote ORDER BY "+order);

            //on affiche les lignes

            while (rst.next())
                System.out.println(rst.getInt(1) + " "+ rst.getString(2) + " "+ rst.getInt(3) +" "+ rst.getString(4) + " "+ rst.getInt(5) + " "+ rst.getInt(6));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean displayFlightTable(Flight order){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM Flight ORDER BY "+order);

            //on affiche les lignes

            while (rst.next()) {
                System.out.println(rst.getInt(1) + " " + rst.getString(2) + " " + rst.getString(3) + " " + rst.getString(5) + " " + rst.getDate(6) + " " + rst.getDate(7) + " " + rst.getDate(8));
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean displayTieFighterTable(TieFighter order){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM TieFighter ORDER BY "+order);

            //on affiche les lignes

            while (rst.next())
                System.out.println(rst.getInt(1) + " "+ rst.getString(2) + " "+ rst.getBoolean(3) + " " + rst.getString(4));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Créer un user
    //Si officier alors le user n'est pas ajouté à la table Pilote
    //si pilote alors le user est ajouté à la table Pilote

    public static boolean createUser(int pilotID, UserPost post, String name, String username, Integer age, String salt, String hashed) throws Exception{
        try{
            Connection con = getConnection();

            Statement stmt = con.createStatement();

            // On vérifie que l'id n'est pas déjà utilisé

            ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE id='"+pilotID+"'");

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("id deja existant.");
                return true;
            }

            // idem pour le username

            rst = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username='"+username+"'");

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("username deja existant.");
                return true;
            }

            // si les informations précédentes ne sont pas prises, on insert le nouveau créateur

            if (post == Pilot) {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Pilote VALUES ('"+pilotID+"', '"+name+"', '"+age+"', 'safe', 0, 0, 0)");
            pstmt.executeUpdate();
            }

            PreparedStatement pstmt1 = con.prepareStatement("INSERT INTO User VALUES ('" + pilotID + "', '" + username + "', '" + salt + "', '"+ hashed +"' , '" + post + "')");
            pstmt1.executeUpdate();

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

    public static boolean assignFlight(String pilotUsername, int fighterID, int flightID, String mission, String startDate, String endRent) throws Exception{
        try{
            Connection con = getConnection();

            //on vérifie la disponibilité du vaisseau

            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT inFlight,Status FROM TieFighter WHERE fighterID="+fighterID);

            rst.next();
            if (rst.getString("status")=="Destroyed" || rst.getInt("inFlight")!=0){
            System.out.println("Tie fighter not available");
            return true;
            }

            Statement stmt1 = con.createStatement();
            ResultSet rst1 = stmt1.executeQuery("SELECT inFlight,Status FROM Pilote WHERE id=(SELECT id FROM User WHERE username='"+pilotUsername+"')");

            rst1.next();
            if (rst1.getString("status") == "Dead" || rst1.getInt("inFlight")!=0){
                System.out.println("Pilot not available");
                return true;
            }

            rst = stmt.executeQuery("SELECT COUNT(*) FROM Flight WHERE flightID="+flightID);

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("id deja existant.");
                return true;
            }

            // si les informations précédentes sont ok, on insert le nouveau vol

            ResultSet rst2 = stmt.executeQuery("SELECT model FROM TieFighter WHERE fighterID="+fighterID);
            rst2.next();
            String model = rst2.getString(1);

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Flight (`flightID`, `pilotUsername`, `fighterModel`, `fighterID`, `mission`, `start`, `endRent`) VALUES ('"+flightID+"', '"+pilotUsername+"', '"+model+"', '"+fighterID+"', '"+mission+"', '"+startDate+"', '"+endRent+"')");
            pstmt.executeUpdate();

            con.close();

            InFlightUpdate(fighterID, pilotUsername);

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

    public static boolean InFlightUpdate(int fighterID, String pilotUsername){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE TieFighter SET inFlight="+1+" WHERE fighterID="+fighterID);
            pstmt.executeUpdate();

            PreparedStatement pstmt1 = con.prepareStatement("UPDATE Pilote SET inFlight="+1+" WHERE id=(SELECT id FROM User WHERE username='"+pilotUsername+"')");
            pstmt1.executeUpdate();

            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("UpdateInFlight Completed.");
        }    }

    //il faut faire la même chose avec le inFlight du pilote



    public static boolean deletePilot(int id){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Pilote WHERE id="+id);
            pstmt.executeUpdate();
            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("Delete Completed.");
        }
    }

    // ca sert à rien

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

    public static boolean modifyUserPassword(int id, String newSalt, String newHashed){
        try{
            Connection con = getConnection();

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

    public static boolean DoneFighterStatus(int id, FighterStatus Status){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE TieFighter SET status='"+Status+"' WHERE fighterID="+id);
            pstmt.executeUpdate();

            PreparedStatement pstmt1 = con.prepareStatement("UPDATE TieFighter SET inFlight=0 WHERE fighterID= (SELECT fighterID FROM Flight WHERE flightID=" + id + ")");
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

    public static boolean displayHistory(String username) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("select * from Flight where pilotUsername='"+username+"'");

            while (rst.next()) {
                System.out.println(rst.getInt(1) + " " + rst.getString(2) + " " + rst.getString(3) + " " + rst.getString(5) + " " + rst.getDate(6) + " " + rst.getDate(7) + " " + rst.getDate(8));
            }

            con.close();
            return true;
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Vol terminé, on met à jour le statut du vaisseau et celui de pilote

    public static boolean flightDone(int id, PilotStatus Pstatus, FighterStatus Fstatus){

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

                ResultSet rst3 = stmt.executeQuery("SELECT endRent FROM Flight WHERE FlightID ="+id);
                rst3.next();
                Date dateEndRent = rst3.getDate(1);

                ResultSet rst1 = stmt.executeQuery("SELECT id FROM User WHERE username = (SELECT pilotUsername FROM Flight WHERE flightID=" + id + ")");
                rst1.next();
                int Pid = rst1.getInt(1);

                ResultSet rst4 = stmt.executeQuery("SELECT endFlight FROM Flight WHERE flightID = "+id);
                rst4.next();
                Date endFlight = rst4.getDate(1);

                if(endFlight.after(dateEndRent)) {
                    DonePilotStatus(Pid, Dead);
                } else {
                    DonePilotStatus(Pid,Pstatus);
                }

                ResultSet rst2 = stmt.executeQuery("SELECT fighterID FROM Flight WHERE flightID=" + id);
                rst2.next();
                int Fid = rst2.getInt(1);
                DoneFighterStatus(Fid, Fstatus);

                if (Fstatus == Destroyed){
                    PreparedStatement pstmt2 = con.prepareStatement("UPDATE Pilote SET shipDestroyed=shipDestroyed+1 WHERE id="+Pid);
                    pstmt2.executeUpdate();
                }

            } else {
                System.out.println("Flight already done");
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            System.out.println("Modify completed.");
        }
    }


// verification du mot de passe
    public static boolean logIn(String username, String pwd) throws Exception{
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
                    System.out.println("Logged in.");
                }
                else {
                    System.out.println("Wrong username or password");
                }
            } else{
                System.out.println("Wrong username or password");
            }
            return true;

        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("Create Completed.");
        }
    }
}
