package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AirlineTest {
    private Airline airline;
    private Route route1;
    private Route route2;

    @BeforeEach
    public void setup() {
        airline = new Airline();
        route1 = new Route(1, "DEL", "LAX", 1100);
        route2 = new Route(2, "DXB", "IST", 300);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, airline.getRouteList().size());
        assertTrue(airline.getRouteList().isEmpty());
        assertEquals(0, airline.getNumPlanes());
    }

    @Test
    public void testAddRouteSuccess() {
        assertTrue(airline.addRoute(route1));
        assertEquals(1, airline.getRouteList().size());
        assertEquals(route1, airline.getRouteList().get(0));

        assertTrue(airline.addRoute(route2));
        assertEquals(2, airline.getRouteList().size());
        assertEquals(route2, airline.getRouteList().get(1));
    }

    @Test
    public void testAddRouteFail() {
        assertTrue(airline.addRoute(route1));
        assertFalse(airline.addRoute(route1));
    }

    @Test
    public void testDeleteRouteSuccess() {
        assertTrue(airline.addRoute(route1));
        assertTrue(airline.addRoute(route2));
        assertTrue(airline.deleteRoute(1));
        assertEquals(1, airline.getRouteList().size());
        assertEquals(route2, airline.getRouteList().get(0));
        assertTrue(airline.deleteRoute(2));
        assertEquals(0, airline.getRouteList().size());
    }

    @Test
    public void testDeleteRouteFail() {
        assertFalse(airline.deleteRoute(1));
        assertTrue(airline.addRoute(route1));
        assertFalse(airline.deleteRoute(2));
    }

    @Test
    public void testSetRouteList() {
        RouteList newRouteList = new RouteList();
        newRouteList.add(route1);
        airline.setRouteList(newRouteList);
        assertEquals(newRouteList, airline.getRouteList());
    }

    @Test
    public void testSetNumPlanes() {
        airline.setNumPlanes(30);
        assertEquals(30, airline.getNumPlanes());
    }
}
