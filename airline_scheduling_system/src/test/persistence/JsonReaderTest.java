package persistence;

import model.Airline;
import model.RouteList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Airline airline = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAirline() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAirline.json");
        try {
            Airline airline = reader.read();
            assertEquals(0, airline.getNumPlanes());
            assertEquals(0, airline.getRouteList().size());
            assertTrue(airline.getRouteList().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAirline() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAirline.json");
        try {
            Airline airline = reader.read();
            assertEquals(30, airline.getNumPlanes());
            RouteList routeList = airline.getRouteList();
            assertEquals(2, routeList.size());
            checkRoute(1234, "DEL", "YVR",
                    null, null, 300, routeList.get(0));
            checkRoute(5678, "YVZ", "LAX",
                    null, null, 200, routeList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
