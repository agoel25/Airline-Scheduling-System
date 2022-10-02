package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represents a list of routes which the airline currently offers. Also holds
// the number of planes that the airline has available.
public class Airline implements Writable {
    private RouteList routeList;  // list of routes currently in service
    private int numPlanes;        // number of planes available

    // EFFECTS: routeList is set to a new empty RouteList, numPlanes
    //          is set to 0.
    public Airline() {
        this.routeList = new RouteList();  // creates a new RouteList with no routes
        this.numPlanes = 0;                // initializes numPlanes to 0
    }

    // MODIFIES: this
    // EFFECTS: if route is not already in the list, adds the provided route
    //          and returns true, else returns false
    public boolean addRoute(Route r) {
        for (Route route : this.routeList) {
            if (route.getId() == r.getId()) {
                return false;
            }
        }
        this.routeList.add(r);
        EventLog.getInstance().logEvent(new Event("Route with ID: " + r.getId() + " was added to the airline"));
        return true;
    }

    // MODIFIES: this
    // EFFECTS: finds a route with a matching id in the list,
    //          if route is in the list, deletes it and returns true
    //          else returns false
    public boolean deleteRoute(int id) {
        for (Route r : this.routeList) {
            if (r.getId() == id) {
                this.routeList.remove(r);
                EventLog.getInstance().logEvent(new Event("Route with ID: " + r.getId()
                        + " was removed from the airline"));
                return true;
            }
        }
        return false;
    }

    public RouteList getRouteList() {
        return this.routeList;
    }

    public int getNumPlanes() {
        return this.numPlanes;
    }

    public void setRouteList(RouteList routeList) {
        this.routeList = routeList;
    }

    public void setNumPlanes(int numPlanes) {
        this.numPlanes = numPlanes;
        EventLog.getInstance().logEvent(new Event("Plane count was updated to: " + numPlanes));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("numPlanes", numPlanes);
        json.put("routeList", routeListToJson());
        return json;
    }

    // EFFECTS: returns routes in this airline as a JSON array
    private JSONArray routeListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Route route : routeList) {
            jsonArray.put(route.toJson());
        }

        return jsonArray;
    }
}
