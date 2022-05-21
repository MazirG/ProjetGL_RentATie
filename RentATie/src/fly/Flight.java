package fly;


import status.TieModel;

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

    public void setPilotID(int pilotID) {
        this.pilotID = pilotID;
    }

    public TieModel getFighterModel() {
        return fighterModel;
    }

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

        public int getFlightID() {
            return flightID;
        }

        public void setFlightID(int flightID) { this.flightID = flightID; }

        public String getMissionName() {
            return missionName;
        }

        public void setMissionName(String missionName) {
            this.missionName = missionName;
        }

        public String getStart() {return start;}

        public void setStart(String start) {this.start = start;}

        public String getEndRent() {return endRent; }

        public void setEndRent(String endRent) {this.endRent = endRent;}

        public String getEndFlight() {return endFlight; }

        public void setEndFlight(String endFlight) {this.endFlight = endFlight; }

        public int getPilotID() {
            return pilotID;
        }

        public void setPilotID(String piloteID) {
            this.pilotID = pilotID;
        }

        public int getFighterID() {
            return fighterID;
        }

        public void setFighterID(int fighterID) {
            this.fighterID = fighterID;
        }

    public int getFlightDuration() {
        return flightDuration;
    }

    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }

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
