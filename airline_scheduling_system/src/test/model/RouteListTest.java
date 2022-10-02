package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RouteListTest {
    private RouteList routeList;

    @Test
    public void testConstructor() {
        routeList = new RouteList();
        assertTrue(routeList.isEmpty());
    }
}
