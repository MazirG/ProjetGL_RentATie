package fly;

import status.FighterStatus;
import status.TieModel;

/**
 * Represents a TieFighter
 */
public class TieFighter {

        private int fighterId;
        private TieModel model;
        private boolean inFlight;
        private FighterStatus status;

        public TieFighter(int fighterId, TieModel model, boolean inFlight, FighterStatus status) {
            this.fighterId = fighterId;
            this.model = model;
            this.inFlight = inFlight;
            this.status = status;
        }

    /**
     * @return current tie ID (unique)
     */
        public int getFighterId() {
            return fighterId;
        }

    /**
     * sets the fighter ID
     * @param fighterId fighter id to set (must be a not taken id)
     */
        public void setFighterId(int fighterId) {
            this.fighterId = fighterId;
        }

    /**
     * @return current tie model
     */
        public TieModel getModel() {
            return model;
        }

    /**
     * sets the tie model
     * @param model the tie model to set
     */
        public void setModel(TieModel model) {
            this.model = model;
        }

    /**
     * @return current tie availability
     */
        public boolean isInFlight() {
            return inFlight;
        }

    /**
     * sets if the tie is in flight or not
     * @param inFlight the state of the tie to set
     */
        public void setInFlight(boolean inFlight) {
            this.inFlight = inFlight;
        }

    /**
     * @return current tie Status
     */
        public FighterStatus getStatus() {
            return status;
        }

    /**
     * sets the tie status
     * @param status the tie status to set
     */
        public void setStatus(FighterStatus status) {
            this.status = status;
        }

    /**
     * @return the current tie fighter id, his model, if it is in flight, his status
     */
    @Override
    public String toString() {
        return "TieFighter{" +
                "fighterId=" + fighterId +
                ", model=" + model +
                ", inFlight=" + inFlight +
                ", status=" + status +
                '}';
    }
}
