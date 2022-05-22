package fly;


import status.TieModel;

/**
 * Represents a flight
 */
public class Flight {
        private int flightID;
        private int pilotID;
        private TieModel fighterModel;
        private int fighterID;
        private String missionName;
        private String start;
        private String endRent;
        private String endFlight;

        private int flightDuration;

    /**
     * @return the fighter used for the current flight model
     */
    public TieModel getFighterModel() {
        return fighterModel;
    }

    /**
     * sets the tie status
     * @param fighterModel the tie used for the current flight model to set
     */
    public void setFighterModel(TieModel fighterModel) {
        this.fighterModel = fighterModel;
    }

    public Flight(int flightID, int pilotID, TieModel fighterModel, int fighterID, String missionName, String start, String endRent, String endFlight, int flightDuration) {
            this.flightID = flightID;
            this.pilotID=pilotID;
            this.fighterModel=fighterModel;
            this.fighterID=fighterID;
            this.missionName = missionName;
            this.start = start;
            this.endRent = endRent;
            this.endFlight = endFlight;
            this.flightDuration= flightDuration;
        }

    /**
     * @return the current flight id
     */
        public int getFlightID() {
            return flightID;
        }

    /**
     * sets the flight id
     * @param flightID the flight id to set
     */
        public void setFlightID(int flightID) { this.flightID = flightID; }

    /**
     * @return  the name of the mission name
     */
        public String getMissionName() {
            return missionName;
        }

    /**
     * sets the flight mission name
     * @param missionName the mission name to set
     */
        public void setMissionName(String missionName) {
            this.missionName = missionName;
        }

    /**
     * @return the start date of the current flight
     */
        public String getStart() {return start;}

    /**
     * sets the flight start date
     * @param start the start date to set
     */
        public void setStart(String start) {this.start = start;}

    /**
     * @return the date of the end of the tie rent
     */
        public String getEndRent() {return endRent; }

    /**
     * sets the date of the end of the tie rent
     * @param endRent the date of the end of the tie rent to set
     */
        public void setEndRent(String endRent) {this.endRent = endRent;}

    /**
     * @return the end of the current flight
     */
        public String getEndFlight() {return endFlight; }

    /**
     * sets the date of the end of the current flight
     * @param endFlight the date of the end of the current flight to set
     */
        public void setEndFlight(String endFlight) {this.endFlight = endFlight; }

    /**
     * @return  pilot assigned to the current flight id
     */
        public int getPilotID() {
            return pilotID;
        }

    /**
     * sets the pilot assigned to the flight id
     * @param pilotID the pilot assigned to the flight id to set
     */
        public void setPilotID(int pilotID) {
            this.pilotID = pilotID;
        }

    /**
     * @return fighter used for the current flight id
     */
        public int getFighterID() {
            return fighterID;
        }

    /**
     * sets the fighter id
     * @param fighterID the fighter used for the current flight id to set
     */
        public void setFighterID(int fighterID) {
            this.fighterID = fighterID;
        }

    /**
     * @return current flight duration
     */
    public int getFlightDuration() {
        return flightDuration;
    }

    /**
     * sets the flight duration
     * @param flightDuration the duration to set
     */
    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

    /**
     * @return the current flight id, the pilot of the flight id, the fighter of the flight id and its model, the mission name, the start date, the end of the rent/flight date
     */
    @Override
    public String toString() {
        return "Flight{" +
                "flightID=" + flightID +
                ", pilotID='" + pilotID + '\'' +
                ", fighterModel=" + fighterModel +
                ", fighterID=" + fighterID +
                ", missionName='" + missionName + '\'' +
                ", start='" + start + '\'' +
                ", endRent='" + endRent + '\'' +
                ", endFlight='" + endFlight + '\'' +
                '}';
    }
}
