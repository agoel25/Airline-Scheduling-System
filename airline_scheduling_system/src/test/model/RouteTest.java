package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

public class RouteTest {
    private Route testRoute;

    @BeforeEach
    public void setup() {
        testRoute = new Route(2503, "DEL", "LAX", 600);
    }

    @Test
    public void testConstructor() {
        assertEquals(2503, testRoute.getId());
        assertEquals("DEL", testRoute.getDepartAirport());
        assertEquals("LAX", testRoute.getArriveAirport());
        assertNull(testRoute.getDepartTime());
        assertNull(testRoute.getArriveTime());
        assertEquals(600, testRoute.getDuration());
    }

    @Test
    public void testSetDepartAndArriveTime() {
        Time departTime = new Time(1, 30, 0);
        Time arriveTime = new Time(11, 30, 0);
        testRoute.setDepartAndArriveTime(departTime);
        assertEquals(departTime, testRoute.getDepartTime());
        assertEquals(arriveTime, testRoute.getArriveTime());
    }

    @Test
    public void testSetID() {
        testRoute.setId(2);
        assertEquals(2, testRoute.getId());
    }

    @Test
    public void testSetDepartAirport() {
        testRoute.setDepartAirport("DXB");
        assertEquals("DXB", testRoute.getDepartAirport());
    }

    @Test
    public void testSetArriveAirport() {
        testRoute.setArriveAirport("DXB");
        assertEquals("DXB", testRoute.getArriveAirport());
    }

    @Test
    public void testSetDuration() {
        testRoute.setDuration(630);
        assertEquals(630, testRoute.getDuration());
    }
}
