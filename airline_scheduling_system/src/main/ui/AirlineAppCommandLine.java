package ui;

import model.Airline;
import model.Route;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Airline flight scheduling application
// (ui code inspired from TellerApp.java in https://github.students.cs.ubc.ca/CPSC210/TellerApp)
// (persistence code inspired from WorkRoomApp.java in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo)
public class AirlineAppCommandLine {
    private static final String JSON_STORE = "./data/airline.json";
    private Airline airline;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the airline application
    public AirlineAppCommandLine() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runApp() {
        boolean quit = false;
        String command;

        initialize();

        while (!quit) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                quit = true;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nApplication has successfully been quit!");
    }

    // MODIFIES: this
    // EFFECTS: processes user input according to proper functionality
    private void processCommand(String command) {
        switch (command) {
            case "a":
                doAddRoute();
                break;
            case "d":
                doDeleteRoute();
                break;
            case "v":
                doViewRouteList();
                break;
            case "c":
                doChangeNumPlanes();
                break;
            case "s":
                saveAirline();
                break;
            case "l":
                loadAirline();
                break;
            default:
                System.out.println("Invalid selection");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a new Airline object and also a scanner object
    //          to take input
    private void initialize() {
        airline = new Airline();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays the menu for functionality options to user
    private void displayMenu() {
        System.out.println("\nSelect option and input appropriate letter:");
        System.out.println("\ta -> Add a new route");
        System.out.println("\td -> Delete an existing route");
        System.out.println("\tv -> View all routes");
        System.out.println("\tc -> Change the number of airplanes");
        System.out.println("\ts -> Save airline information to file");
        System.out.println("\tl -> Load airline information from file");
        System.out.println("\tq -> Quit application");
    }

    // MODIFIES: this
    // EFFECTS: creates a new route and adds it to the list of routes
    //          if route is already present, exclaims failure
    private void doAddRoute() {
        System.out.println("Add Route ->");
        System.out.println("Enter the route info when prompted:");

        System.out.println("Enter the route ID: ");
        int id = input.nextInt();

        System.out.println("Enter the departure airport: ");
        String departAirport = input.next();

        System.out.println("Enter the arrival airport: ");
        String arriveAirport = input.next();

        System.out.println("Enter the duration (in minutes): ");
        int duration = input.nextInt();

        if (airline.addRoute(new Route(id, departAirport, arriveAirport, duration))) {
            System.out.println("Route successfully added!");
        } else {
            System.out.println("Route addition failed!");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a pre-existing route from airline's data
    //          if route is not already present, exclaims failure
    private void doDeleteRoute() {
        System.out.println("Delete Route ->");
        System.out.println("Enter the route ID which you would like to delete: ");
        int id = input.nextInt();
        if (airline.deleteRoute(id)) {
            System.out.println("Route successfully deleted!");
        } else {
            System.out.println("Route deletion failed!");
        }
    }

    // EFFECTS: prints the list of routes the airline is currently serving
    private void doViewRouteList() {
        System.out.println("Flight routes: ");
        System.out.println("-------------------------------------------------------------------------"
                           + "---------------------------------------------------");
        for (Route r : airline.getRouteList()) {
            System.out.println("Route ID: " + r.getId() + " | " + "Departure Airport: " + r.getDepartAirport()
                                + " | " + "Arrival Airport: " + r.getArriveAirport() + " | " + "Departure Time: "
                                + r.getDepartTime() + " | " + "Arrival Time: " + r.getArriveTime() + " | "
                                + "Duration: " + r.getDuration());
        }
        System.out.println("-------------------------------------------------------------------------"
                           + "---------------------------------------------------");
        System.out.println("Number of planes = " + airline.getNumPlanes());
        System.out.println("-------------------------------------------------------------------------"
                           + "---------------------------------------------------");
    }

    // MODIFIES: this
    // EFFECTS: changes the number of planes in the Airline class
    private void doChangeNumPlanes() {
        System.out.println("Change Number of Planes -> ");
        System.out.println("Enter number of planes: ");
        int numPlanes = input.nextInt();
        airline.setNumPlanes(numPlanes);
        System.out.println("Plane count updated!");
    }

    // EFFECTS: saves the airline to file
    private void saveAirline() {
        try {
            jsonWriter.open();
            jsonWriter.write(airline);
            jsonWriter.close();
            System.out.println("Saved airline information to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads airline from file
    private void loadAirline() {
        try {
            airline = jsonReader.read();
            System.out.println("Loaded pre-saved airline information from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
