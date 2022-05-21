package login;

public class Officer extends User {

    private int officerID;

    public Officer(int id, String username, String password) {
        super(id, username, password);
        this.officerID = id;
    }

    public int getOfficerID() {
        return officerID;
    }

    public void setOfficerID(int officerID) {
        this.officerID = officerID;
    }
}