public class Flight {
    private int flightID;
    String pilotID;
    int fighterID;
    private String missionName;
    private String dateBegin;
    private String dateEnd;
    int flightDuration;
    private int rentDuration;


    public Flight(int flightID, String pilotID,int fighterID,String missionName, String dateBegin, String dateEnd, int flightDuration, int rentDuration) {
        this.flightID = flightID;
        this.pilotID=pilotID;
        this.fighterID=fighterID;
        this.missionName = missionName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.flightDuration = flightDuration;
        this.rentDuration = rentDuration;
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

    public int getRentDuration() {
        return rentDuration;
    }

    public void setRentDuration(int rentDuration) {
        this.rentDuration = rentDuration;
    }

    public String getPilotID() {
        return pilotID;
    }

    public void setPilotID(String pilotID) {
        this.pilotID = pilotID;
    }

    public int getFighterID() {
        return fighterID;
    }

    public void setFighterID(int fighterID) {
        this.fighterID = fighterID;
    }
}
