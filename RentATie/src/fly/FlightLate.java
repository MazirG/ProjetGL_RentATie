package fly;

import java.sql.Date;
import java.time.LocalDate;

/**
 * FlightLate object
 * This object is only used in the whosLate window, as a display object inside a TableView
 * Exists to simplify the print of Flight that are late, because of the working conditions of a TableView that reads objects
 */
public class FlightLate {
    private int id;
    private int late;
    private String begin;
    private String end;

    /**
     * Constructor of FlightLate:
     * Set the flight information that it needs to print from the flight object (id,begin,end)
     * The late duration in date is set automatically based on the begin and end fields
     * @param flight the flight used to give parameters to the FlightDone object
     */
    public FlightLate(Flight flight){
        this.id=flight.getFlightID();
        this.begin= flight.getStart();
        this.end= flight.getEndRent();
        /*LocalDate dateBegin = LocalDate.parse(flight.getStart());
        LocalDate dateBack = LocalDate.parse(flight.getEndRent());*/
        Date endDate = Date.valueOf(flight.getEndRent());
        Date date_of_today = Date.valueOf(LocalDate.now());
        long difference_In_Time = date_of_today.getTime() - endDate.getTime();
        this.late = (int) ((difference_In_Time / (1000 * 60 * 60 * 24)) % 365);


    }

    /**
     * Getter for the id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the late duration (in numbers of day)
     * @return late
     */
    public int getLate() {
        return late;
    }

    /**
     * Getter for the begin date (date format DD-MM-YYYY)
     * @return begin
     */
    public String getBegin() {
        return begin;
    }

    /**
     * Getter for the end date (date format DD-MM-YYYY)
     * @return late
     */
    public String getEnd() {
        return end;
    }
}
