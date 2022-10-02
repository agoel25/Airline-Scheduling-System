package model;

import java.util.ArrayList;

// Represents a list of routes
public class RouteList extends ArrayList<Route> {
    private ArrayList<Route> routeList;

    // EFFECTS: constructs a new RouteList which is an empty
    //          ArrayList of Route(s)
    public RouteList() {
        routeList = new ArrayList<>();
    }
}
