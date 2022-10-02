package model;

import org.json.JSONObject;
import persistence.Writable;

import java.sql.Time;

// Represents a route, containing route ID, departure airport, arrival airport,
// flight duration, departure time and arrival time. Departure and arrival times
// will initially be set to null but will be decided by the algorithm when run.
public class Route implements Writable {
    private int id;                // unique 4-digit route id
    private String departAirport;  // 3-letter code for the departure airport
    private String arriveAirport;  // 3-letter code for the arrival airport
    private Time departTime;       // time of departure in 24-hour format
    private Time arriveTime;       // time of arrival in 24-hour format
    private int duration;          // duration time of the flight in minutes

    // REQUIRES: id is a UNIQUE id, departAirport and arriveAirport are non-empty
    //           Strings, 0 < duration < 24
    // EFFECTS: all fields are set to corresponding values from the parameter list,
    //          departTime and arriveTime are set to null as they have not been
    //          decided yet
    public Route(int id, String departAirport, String arriveAirport, int duration) {
        this.id = id;
        this.departAirport = departAirport;
        this.arriveAirport = arriveAirport;
        this.departTime = null;
        this.arriveTime = null;
        this.duration = duration;
    }

    public int getId() {
        return this.id;
    }

    public String getDepartAirport() {
        return this.departAirport;
    }

    public String getArriveAirport() {
        return this.arriveAirport;
    }

    public Time getDepartTime() {
        return this.departTime;
    }

    public Time getArriveTime() {
        return this.arriveTime;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDepartAirport(String departAirport) {
        this.departAirport = departAirport;
    }

    public void setArriveAirport(String arriveAirport) {
        this.arriveAirport = arriveAirport;
    }

    // EFFECTS: sets departTime field to departTime from parameter, also calculates
    //          arrival time by adding duration to departure time and sets it
    //          to arriveTime field
    public void setDepartAndArriveTime(Time departTime) {
        this.departTime = departTime;
        this.arriveTime = this.departTime;
        this.arriveTime.setHours(this.arriveTime.getHours() + this.duration / 60);
        this.arriveTime.setMinutes(this.arriveTime.getMinutes() + this.duration % 60);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("departAirport", departAirport);
        json.put("arriveAirport", arriveAirport);
        json.put("duration", duration);
        return json;
    }
}
