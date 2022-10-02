package persistence;

import model.Airline;
import model.Route;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads an airline from JSON data stored in file
// (code inspired from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads airline from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Airline read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAirline(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses airline from JSON object and returns it
    private Airline parseAirline(JSONObject jsonObject) {
        Airline airline = new Airline();
        addRouteList(airline, jsonObject);
        addNumPlanes(airline, jsonObject);
        return airline;
    }

    // MODIFIES: airline
    // EFFECTS: parses routeList from JSON object and adds them to airline
    private void addRouteList(Airline airline, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("routeList");
        for (Object json : jsonArray) {
            JSONObject nextRoute = (JSONObject) json;
            addRoute(airline, nextRoute);
        }
    }

    // MODIFIES: airline
    // EFFECTS: parses route from JSON object and adds them to airline
    private void addRoute(Airline airline, JSONObject jsonObject) {
        int id = jsonObject.getInt("id");
        String departAirport = jsonObject.getString("departAirport");
        String arriveAirport = jsonObject.getString("arriveAirport");
        //Time departTime = Time.valueOf(jsonObject.getString("departTime"));
        //Time arriveTime = Time.valueOf(jsonObject.getString("arriveTime"));
        int duration = jsonObject.getInt("duration");
        Route route = new Route(id, departAirport, arriveAirport, duration);
        airline.addRoute(route);
    }

    // MODIFIES: airline
    // EFFECTS: parses numPlanes from JSON object and adds them to airline
    private void addNumPlanes(Airline airline, JSONObject jsonObject) {
        int numPlanes = jsonObject.getInt("numPlanes");
        airline.setNumPlanes(numPlanes);
    }
}
