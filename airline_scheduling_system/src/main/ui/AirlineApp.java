package ui;

import model.Airline;
import model.EventLog;
import model.Event;
import model.Route;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Airline flight scheduling application
public class AirlineApp {

    // model and persistence fields
    private static final String JSON_STORE = "./data/airline.json";
    protected Airline airline;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // gui home window fields
    private JFrame homeFrame;
    private JPanel homePanel;
    private JLabel idSearchLabel;
    private JTextField searchTextField;
    private JButton searchButton;
    private JButton backButton;
    private JList homeList;
    private DefaultListModel listModel;
    private JButton addRouteButton;
    private JButton deleteRouteButton;
    private JButton saveDataButton;
    private JButton loadDataButton;
    private JLabel planeCountLabel;
    private JTextField planeCountTextField;
    private JButton planeCountButton;
    private static final String LOGO = "./data/images/logo.png";
    private ImageIcon logoIcon;
    private JLabel imageLabel;
    private ImageIcon alertSuccessIcon;
    private ImageIcon alertFailureIcon;
    private ImageIcon airplaneIcon;

    // gui add route popup fields
    private JTextField idField;
    private JTextField departAirportField;
    private JTextField arriveAirportField;
    private JTextField durationField;
    private JPanel addRoutePopupPanel;

    // MODIFIES: this
    // EFFECTS: creates a new airline application
    @SuppressWarnings("methodlength")
    public AirlineApp() {
        initialize();
        homeFrame.setVisible(true);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSearch();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshList();
                searchTextField.setText("");
            }
        });

        addRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAddRoute();
            }
        });

        deleteRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doDeleteRoute();
            }
        });

        saveDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doSaveData();
            }
        });

        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLoadData();
            }
        });

        planeCountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doChangeNumPlanes();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Opens a new popup window for user to input the details for new route
    //          then adds the new route to the airline
    private void doAddRoute() {
        initializeAddRoutePopup();

        int result = JOptionPane.showConfirmDialog(homeFrame, addRoutePopupPanel,
                "Add Route", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, airplaneIcon);
        if (result == JOptionPane.OK_OPTION) {
            int id = Integer.parseInt(idField.getText());
            String departAirport = departAirportField.getText();
            String arriveAirport = arriveAirportField.getText();
            int duration = Integer.parseInt(durationField.getText());
            Route route = new Route(id, departAirport, arriveAirport, duration);
            if (airline.addRoute(route)) {
                refreshList();
                JOptionPane.showMessageDialog(homeFrame, "Route Addition Successful", "Notification",
                        JOptionPane.PLAIN_MESSAGE, alertSuccessIcon);
            } else {
                JOptionPane.showMessageDialog(homeFrame, "Route Addition Unsuccessful", "Notification",
                        JOptionPane.PLAIN_MESSAGE, alertFailureIcon);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Deletes the route selected from the JList interface
    private void doDeleteRoute() {
        String idString = homeList.getSelectedValue().toString().substring(4,8);
        int id = Integer.parseInt(idString);

        if (airline.deleteRoute(id)) {
            refreshList();
            JOptionPane.showMessageDialog(homeFrame, "Route removed successfully", "Notification",
                    JOptionPane.PLAIN_MESSAGE, alertSuccessIcon);
        } else {
            JOptionPane.showMessageDialog(homeFrame, "Route removal unsuccessful", "Notification",
                    JOptionPane.PLAIN_MESSAGE, alertFailureIcon);
        }
    }

    // EFFECTS: saves the airline to file
    private void doSaveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(airline);
            jsonWriter.close();
            JOptionPane.showMessageDialog(homeFrame, "Saved airline information to " + JSON_STORE,
                    "Notification", JOptionPane.PLAIN_MESSAGE, alertSuccessIcon);
        } catch (FileNotFoundException exception) {
            JOptionPane.showMessageDialog(homeFrame, "Unable to write to file: " + JSON_STORE,
                    "Notification", JOptionPane.PLAIN_MESSAGE, alertFailureIcon);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the airline data from file, displays it appropriately in the gui
    private void doLoadData() {
        try {
            airline = jsonReader.read();
            planeCountLabel.setText("Plane Count: " + airline.getNumPlanes());
            refreshList();
            planeCountTextField.setText("");
            JOptionPane.showMessageDialog(homeFrame, "Loaded pre-saved airline information from " + JSON_STORE,
                    "Notification", JOptionPane.PLAIN_MESSAGE, alertSuccessIcon);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(homeFrame, "Unable to read from file: " + JSON_STORE, "Notification",
                    JOptionPane.PLAIN_MESSAGE, alertFailureIcon);
        }
    }

    // MODIFIES: this
    // EFFECTS: changes the number of planes in the airline
    private void doChangeNumPlanes() {
        String newPlaneCount = planeCountTextField.getText();
        airline.setNumPlanes(Integer.parseInt(newPlaneCount));
        planeCountLabel.setText("Plane Count: " + newPlaneCount);
        planeCountTextField.setText("");
    }

    // MODIFIES: this
    // EFFECTS: refreshes the JList by filling it with appropriate airline information
    private void refreshList() {
        homePanel.remove(homeList);
        listModel = new DefaultListModel();
        for (int i = 0; i < airline.getRouteList().size(); i++) {
            Route r = airline.getRouteList().get(i);
            listModel.addElement("ID: " + r.getId() + " | " + "Depart Airport: " + r.getDepartAirport()
                    + " | " + "Arrive Airport: " + r.getArriveAirport() + " | " + "Depart Time: "
                    + r.getDepartTime() + " | " + "Arrive Time: " + r.getArriveTime() + " | "
                    + "Duration: " + r.getDuration());
        }

        homeList = new JList(listModel);
        homeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        homeList.setSelectedIndex(0);
        homeList.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        homeList.setBounds(6, 39, 704, 346);
        homePanel.add(homeList);
        homePanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: looks for route with the input ID, updates the JList accordingly
    public void doSearch() {
        homePanel.remove(homeList);
        listModel = new DefaultListModel();
        String searchString = searchTextField.getText();
        for (int i = 0; i < airline.getRouteList().size(); i++) {
            Route r = airline.getRouteList().get(i);
            if (!searchString.isEmpty() && r.getId() == Integer.parseInt(searchString)) {
                listModel.addElement("ID: " + r.getId() + " | " + "Depart Airport: " + r.getDepartAirport()
                        + " | " + "Arrive Airport: " + r.getArriveAirport() + " | " + "Depart Time: "
                        + r.getDepartTime() + " | " + "Arrive Time: " + r.getArriveTime() + " | "
                        + "Duration: " + r.getDuration());
            }
        }
        homeList = new JList(listModel);
        homeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        homeList.setSelectedIndex(0);
        homeList.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        homeList.setBounds(6, 39, 704, 346);
        homePanel.add(homeList);
        homePanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: initialises all the model and main ui window fields
    @SuppressWarnings("methodlength")
    private void initialize() {
        airline = new Airline();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        homeFrame = new JFrame("Airline App");
        homeFrame.setBounds(300, 200, 930, 465);
        homeFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        homeFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.out.println("------------ Event Log ------------\n");
                for (Event nextEvent : EventLog.getInstance()) {
                    System.out.println(nextEvent.toString() + "\n");
                }
                System.out.println("-----------------------------------\n");
                System.exit(0);
            }
        });

        homePanel = new JPanel();
        homePanel.setBounds(6, 6, 930, 465);
        homeFrame.getContentPane().add(homePanel);
        homePanel.setLayout(null);

        idSearchLabel = new JLabel("ID:");
        idSearchLabel.setBounds(309, 11, 26, 16);
        homePanel.add(idSearchLabel);

        searchTextField = new JTextField();
        searchTextField.setBounds(336, 6, 130, 26);
        homePanel.add(searchTextField);
        searchTextField.setColumns(10);

        searchButton = new JButton("Search");
        searchButton.setBounds(463, 6, 117, 29);
        homePanel.add(searchButton);

        backButton = new JButton("Back");
        backButton.setBounds(570, 6, 117, 29);
        homePanel.add(backButton);

        homeList = new JList();
        refreshList();

        addRouteButton = new JButton("Add Route");
        addRouteButton.setBounds(0, 390, 355, 43);
        homePanel.add(addRouteButton);

        deleteRouteButton = new JButton("Delete Route");
        deleteRouteButton.setBounds(355, 390, 355, 43);
        homePanel.add(deleteRouteButton);

        saveDataButton = new JButton("Save Data");
        saveDataButton.setBounds(714, 203, 210, 43);
        homePanel.add(saveDataButton);

        loadDataButton = new JButton("Load Data");
        loadDataButton.setBounds(714, 250, 210, 43);
        homePanel.add(loadDataButton);

        planeCountLabel = new JLabel("Plane Count:");
        planeCountLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        planeCountLabel.setBounds(722, 316, 202, 26);
        homePanel.add(planeCountLabel);

        planeCountTextField = new JTextField();
        planeCountTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        planeCountTextField.setBounds(722, 342, 202, 43);
        homePanel.add(planeCountTextField);
        planeCountTextField.setColumns(10);

        planeCountButton = new JButton("Change Plane Count");
        planeCountButton.setBounds(722, 390, 202, 43);
        homePanel.add(planeCountButton);

        logoIcon = new ImageIcon(LOGO);
        imageLabel = new JLabel();
        imageLabel.setBounds(714, 11, 210, 190);
        imageLabel.setIcon(logoIcon);
        homePanel.add(imageLabel);

        alertFailureIcon = new ImageIcon("./data/images/alertFailureIcon.png");
        alertSuccessIcon = new ImageIcon("./data/images/alertSuccessIcon.png");
        airplaneIcon = new ImageIcon("./data/images/airplaneIcon.png");
    }

    // MODIFIES: this
    // EFFECTS: initialises all the popup window fields
    public void initializeAddRoutePopup() {
        idField = new JTextField(5);
        departAirportField = new JTextField(5);
        arriveAirportField = new JTextField(5);
        durationField = new JTextField(5);
        addRoutePopupPanel = new JPanel();
        addRoutePopupPanel.add(new JLabel("ID: "));
        addRoutePopupPanel.add(idField);
        addRoutePopupPanel.add(new JLabel("Departure Airport:"));
        addRoutePopupPanel.add(departAirportField);
        addRoutePopupPanel.add(new JLabel("Arrival Airport:"));
        addRoutePopupPanel.add(arriveAirportField);
        addRoutePopupPanel.add(new JLabel("Duration:"));
        addRoutePopupPanel.add(durationField);
    }
}
