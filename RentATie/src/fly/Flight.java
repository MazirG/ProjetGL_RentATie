package fly;


public class Flight {
        private int flightID;
        private int pilotID;
        private int fighterID;
        private String missionName;
        private String dateBegin;
        private String dateEnd;
        private String flightDuration;
        private String rentDuration;


        public Flight(int flightID, int pilotID,int fighterID,String missionName, String dateBegin, String dateEnd, String flightDuration, String rentDuration) {
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

        public String getFlightDuration() {
            return flightDuration;
        }

        public void setFlightDuration(String flightDuration) {
            this.flightDuration = flightDuration;
        }

        public String getRentDuration() {
            return rentDuration;
        }

        public void setRentDuration(String rentDuration) {
            this.rentDuration = rentDuration;
        }

        public int getPilotID() {
            return pilotID;
        }

        public void setPilotID(int pilotID) {
            this.pilotID = pilotID;
        }

        public int getFighterID() {
            return fighterID;
        }

        public void setFighterID(int fighterID) {
            this.fighterID = fighterID;
        }

}
