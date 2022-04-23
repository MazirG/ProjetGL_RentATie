package bdd;

import java.sql.*;
import java.util.Scanner;

public interface Request {

    public static Connection getConnection() throws Exception{
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://mysql-rentatie.alwaysdata.net:3306/rentatie_bdd";
            String username = "rentatie";
            String password = "MotDePasseComplique20212022";
            Class.forName(driver);

            Connection conn = DriverManager.getConnection(url,username,password);
            System.out.println("Connected");
            return conn;
        } catch(Exception e){System.out.println(e);}
        return null;
    }

    public static void displayTable(){
        try{
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("SELECT * FROM Pilote");

            //on affiche les lignes

            while (rst.next())
                System.out.println(rst.getInt(1) + " "+ rst.getString(2) + " "+ rst.getInt(3) + " " + rst.getString(4));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createPilot() throws Exception{
        try{
            Connection con = getConnection();

            Scanner scanner = new Scanner( System.in );

            System.out.print( "Veuillez saisir un id : " );
            int idPilot = scanner.nextInt();

            System.out.print( "Veuillez saisir un username : " );
            String username = scanner.next();

            System.out.print( "Veuillez saisir un age : " );
            int age = scanner.nextInt();

            System.out.print( "Veuillez saisir un mot de passe : " );
            String pwd = scanner.next();

            System.out.println("\n_____________");

            Statement stmt = con.createStatement();

            // On vérifie que l'id n'est pas déjà utilisé

            ResultSet rst = stmt.executeQuery("SELECT COUNT(*) FROM Pilote WHERE id="+idPilot);

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("id deja existant.");
                return;
            }

            // idem pour le username

            rst = stmt.executeQuery("SELECT COUNT(*) FROM Pilote WHERE username='"+username+"'");

            rst.next();
            if (rst.getInt(1)>0){
                System.out.println("username deja existant.");
                return;
            }

            // si les informations précédentes ne sont pas prises, on insert le nouveau créateur

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Pilote VALUES ('"+idPilot+"', '"+username+"', '"+age+"', '"+pwd+"')");
            pstmt.executeUpdate();
            con.close();
        } catch(Exception e){System.out.println(e);}
        finally {
            System.out.println("Insert Completed.");
        }
    }

    public static void deletePilot(){
        try{
            Connection con = getConnection();

            Scanner scanner = new Scanner( System.in );

            System.out.print( "Veuillez saisir l'id du pilote à supprimer : " );
            int id = scanner.nextInt();

            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Pilote WHERE id="+id);
            pstmt.executeUpdate();
        } catch(Exception e){System.out.println(e);}
        finally {
            System.out.println("Delete Completed.");
        }
    }

    public static void modifyPilotAge(){
        try{
            Connection con = getConnection();

            Scanner scanner = new Scanner( System.in );

            System.out.print( "Veuillez saisir l'id du pilote à modifier : " );
            int id = scanner.nextInt();

            System.out.print( "Veuillez saisir un nouvel age : " );
            int Age = scanner.nextInt();

            PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET age="+Age+" WHERE id="+id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Modify completed.");
        }
    }

    public static void modifyPilotPassword(){
        try{
            Connection con = getConnection();

            Scanner scanner = new Scanner( System.in );

            System.out.print( "Veuillez saisir l'id du pilote à modifier : " );
            int id = scanner.nextInt();

            System.out.print( "Veuillez saisir un nouveau mdp : " );
            String pwd = scanner.next();

            PreparedStatement pstmt = con.prepareStatement("UPDATE Pilote SET password='"+pwd+"' WHERE id="+id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            System.out.println("Modify completed.");
        }
    }
}
