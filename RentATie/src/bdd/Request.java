//dans la bdd il manque des attribut du pilote
//attention, il faudra aussi modifier les requêtes createPilot et Displaypilot
//faire plusieurs display (selon l'id, le nom, l'age,...)

package bdd;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Request {

    public static Connection getConnection() throws Exception{
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("ressources/conf.properties")) {
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

    public static boolean displayPilotTable(){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM Pilote");

            //on affiche les lignes

            while (rst.next())
                System.out.println(rst.getInt(1) + " "+ rst.getString(2) + " "+ rst.getString(3) + " " + rst.getInt(4) + " " + rst.getString(5));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean displayFlightTable(){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM Flight");

            //on affiche les lignes

            while (rst.next())
                System.out.println(rst.getInt(1) + " "+ rst.getInt(2) + " "+ rst.getInt(3) + " " + rst.getString(4) + " " + rst.getDate(5) + " " + rst.getDate(6) + " " + rst.getTime(7) + " " + rst.getTime(8));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean displayTieFighterTable(){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM TieFighter");

            //on affiche les lignes

            while (rst.next())
                System.out.println(rst.getInt(1) + " "+ rst.getString(2) + " "+ rst.getBoolean(3) + " " + rst.getString(4));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createPilot() throws Exception{
        try{
            Connection con = getConnection();

            Scanner scanner = new Scanner( System.in );

            System.out.print( "Veuillez saisir un id : " );
            int pilotID = scanner.nextInt();

            System.out.print( "Veuillez saisir le prenom du pilote : " );
            String username = scanner.next();

            System.out.print( "Veuillez saisir un username : " );
            String name = scanner.next();

            System.out.print( "Veuillez saisir l'age du pilote : " );
            int age = scanner.nextInt();

            System.out.print( "Veuillez saisir un mot de passe : " );
            String pwd = scanner.next();

            System.out.println("\n_____________");

            Statement stmt = con.createStatement();

            // On vérifie que l'id n'est pas déjà utilisé

            ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM Pilote WHERE id="+pilotID);

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("id deja existant.");
                return true;
            }

            // idem pour le username

            rst = stmt.executeQuery("SELECT COUNT(*) FROM Pilote WHERE username='"+username+"'");

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("username deja existant.");
                return true;
            }

            // si les informations précédentes ne sont pas prises, on insert le nouveau créateur

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Pilote VALUES ('"+pilotID+"', '"+name+"', '"+username+"', '"+age+"', '"+pwd+"')");
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

    public static boolean assignFlight() throws Exception{
        try{
            Connection con = getConnection();
            Scanner scanner = new Scanner( System.in );

            System.out.print( "Veuillez saisir l'id du pilote : " );
            int pilotID = scanner.nextInt();

            System.out.print( "Veuillez saisir l'id du fighter : " );
            int fighterID = scanner.nextInt();

            //on vérifie la disponibilité du vaisseau

            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT inFlight,Status FROM TieFighter WHERE fighterID="+fighterID);

            rst.next();
            if (rst.getString("status")=="Destroyed" || rst.getInt("inFlight")!=0){
            System.out.println("Tie fighter not available");
            return true;
            }

            System.out.print( "Veuillez saisir un id pour le vol : " );
            int flightID = scanner.nextInt();

            System.out.print( "Veuillez saisir le nom de la mission : " );
            String mission = scanner.next();

            System.out.print( "Veuillez saisir la date du début (AAAA-MM-JJ): " );
            String startDate = scanner.next();

            System.out.print( "Veuillez saisir la date du fin : " );
            String endDate = scanner.next();

            System.out.print( "Veuillez saisir la durée de vol (hh:mm:ss) : " );
            String flyDuration = scanner.next();

            System.out.print( "Veuillez saisir la durée de la location (hh:mm:ss) : " );
            String rentDuration = scanner.next();

            System.out.println("\n_____________");

            Statement stmt1 = con.createStatement();

            rst = stmt.executeQuery("SELECT COUNT(*) FROM Flight WHERE flightID="+flightID);

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("id deja existant.");
                return true;
            }

            // si les informations précédentes sont ok, on insert le nouveau vol

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Flight VALUES ('"+flightID+"', '"+pilotID+"', '"+fighterID+"', '"+mission+"', '"+startDate+"', '"+endDate+"', '"+flyDuration+"', '"+rentDuration+"')");
            pstmt.executeUpdate();

            con.close();

            InFlightUpdate(fighterID);

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
    public static boolean InFlightUpdate(int fighterID){
        try{
            Connection con = getConnection();

            PreparedStatement pstmt = con.prepareStatement("UPDATE TieFighter SET inFlight="+1+" WHERE fighterID="+fighterID);
            pstmt.executeUpdate();
            return true;
        } catch(Exception e){
            System.out.println(e);
            return false;
        }
        finally {
            System.out.println("UpdateInFlight Completed.");
        }    }
    //il faut faire la même chose avec le inFlight du pilote

    public static boolean deletePilot(){
        try{
            Connection con = getConnection();

            Scanner scanner = new Scanner( System.in );

            System.out.print( "Veuillez saisir l'id du pilote à supprimer : " );
            int id = scanner.nextInt();

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

    //c'est mieux avec un update
    public static boolean modifyPilotAge(){
        try{
            Connection con = getConnection();

            Scanner scanner = new Scanner( System.in );

            System.out.print( "Veuillez saisir l'id du pilote à modifier : " );
            int id = scanner.nextInt();

            System.out.print( "Veuillez saisir un nouvel age : " );
            int Age = scanner.nextInt();

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

    public static boolean modifyPilotPassword(){
        try{
            Connection con = getConnection();

            Scanner scanner = new Scanner( System.in );

            System.out.print( "Veuillez saisir l'id du pilote à modifier : " );
            int id = scanner.nextInt();

            System.out.print( "Veuillez saisir un nouveau mdp : " );
            String pwd = scanner.next();

            PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET password='"+pwd+"' WHERE id="+id);
            pstmt.executeUpdate();

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        } finally {
            System.out.println("Modify completed.");
        }
    }

    public static boolean displayHistory(int id) {
        try {
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("select * from Flight where pilotID="+id);
            con.close();
            return true;
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
