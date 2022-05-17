package testtest;

import java.sql.*;

public class TestBDD {

    Connection con;
    PreparedStatement pst;
    ResultSet rst;

    public void Connecter() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection( "jdbc:mysql://mysql-rentatie.alwaysdata.net/rentatie_bdd" ,"rentatie","MotDePasseComplique20212022" );
            Statement stmt = con.createStatement();
            rst = stmt.executeQuery("select * from Pilote");
            while (rst.next())
                System.out.println(rst.getInt(1) + " "+ rst.getString(2) + " "+ rst.getString(3) + " " + rst.getInt(4));
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
