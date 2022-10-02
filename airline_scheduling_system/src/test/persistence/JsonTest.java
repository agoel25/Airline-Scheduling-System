package persistence;

import model.Route;

import java.sql.Time;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkRoute(int id, String departAirport, String arriveAirport, Time departTime, Time arriveTime,
                              int duration, Route route) {
        assertEquals(id, route.getId());
        assertEquals(departAirport, route.getDepartAirport());
        assertEquals(arriveAirport, route.getArriveAirport());
        assertEquals(departTime, route.getDepartTime());
        assertEquals(arriveTime, route.getArriveTime());
        assertEquals(duration, route.getDuration());
    }
}
