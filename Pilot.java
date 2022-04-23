import java.sql.*;

public class Pilot {
    String pilotID;
    private String name;
    private int age;
    boolean inFlight;
    PilotStatus status;
    int totalFlight;
    int shipDestroyed;

    public Pilot(String pilotID,String name,int age,boolean inFlight,PilotStatus status,int totalFlight,int shipDestroyed) {
        this.pilotID=pilotID;
        this.name=name;
        this.age=age;
        this.inFlight=inFlight;
        this.status=status;
        this.totalFlight=totalFlight;
        this.shipDestroyed=shipDestroyed;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public PilotStatus getStatusPilot() {
        return status;
    }

    public int getTotalFlight() {
        return totalFlight;
    }

    public int getShipDestroyed() {
        return shipDestroyed;
    }

    public boolean isInFlight() {
        return inFlight;
    }

    public String getPilotID() {
        return pilotID;
    }

    public PilotStatus getStatus() {
        return status;
    }

    public void displayHistory(Pilot p) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://mysql-rentatie.alwaysdata.net/rentatie_bdd", "rentatie", "MotDePasseComplique20212022");

            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("select * from Flight where pilotID="+p.pilotID+"");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}
