package persistence;

import model.Airline;
import model.Route;
import model.RouteList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Airline airline = new Airline();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Airline airline = new Airline();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAirline.json");
            writer.open();
            writer.write(airline);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAirline.json");
            airline = reader.read();
            assertEquals(0, airline.getNumPlanes());
            assertEquals(0, airline.getRouteList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Airline airline = new Airline();
            airline.addRoute(new Route(1234, "DEL", "YVR", 1000));
            airline.addRoute(new Route(5678, "YVZ", "DXB", 800));
            airline.setNumPlanes(24);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAirline.json");
            writer.open();
            writer.write(airline);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAirline.json");
            airline = reader.read();
            assertEquals(24, airline.getNumPlanes());
            RouteList routeList = airline.getRouteList();
            assertEquals(2, routeList.size());
            checkRoute(1234, "DEL", "YVR",
                    null, null, 1000, routeList.get(0));
            checkRoute(5678, "YVZ", "DXB",
                    null, null, 800, routeList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
