public class TieFighter {
    int fighterId;
    private TieModel model;
    boolean inFlight;
    FighterStatus status;

    public TieFighter(int fighterId, TieModel model, boolean inFlight, FighterStatus status) {
        this.fighterId = fighterId;
        this.model = model;
        this.inFlight = inFlight;
        this.status = status;
    }

    public int getFighterId() {
        return fighterId;
    }

    public void setFighterId(int fighterId) {
        this.fighterId = fighterId;
    }

    public TieModel getModel() {
        return model;
    }

    public void setModel(TieModel model) {
        this.model = model;
    }

    public boolean isInFlight() {
        return inFlight;
    }

    public void setInFlight(boolean inFlight) {
        this.inFlight = inFlight;
    }

    public FighterStatus getStatus() {
        return status;
    }

    public void setStatus(FighterStatus status) {
        this.status = status;
    }


}
