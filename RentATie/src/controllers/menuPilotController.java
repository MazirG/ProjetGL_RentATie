package controllers;

import bdd.Request;
import fly.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import status.TieModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Pilot menu
 * A table with all the Flights of the Pilot is available
 * The only other function that Pilot is authorized is to modify is IDS as user
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class menuPilotController extends Controller implements Initializable {

    @FXML
    private Label labelMessage;

    @FXML
    private Button btnDisconnect;

    @FXML
    private Button btnPassword;

    @FXML
    private Button btnFlights;

    @FXML
    private TableColumn<Flight, String> cDateBack;

    @FXML
    private TableColumn<Flight, String> cDateBegin;

    @FXML
    private TableColumn<Flight, String> cDateEnd;

    @FXML
    private TableColumn<Flight, TieModel> cFighterModel;

    @FXML
    private TableColumn<Flight, Integer> cFighterId;

    @FXML
    private TableColumn<Flight, Integer> cId;

    @FXML
    private TableColumn<Flight, String> cMission;

    @FXML
    private TableColumn<Flight, Integer> cPilotId;

    @FXML
    private TableColumn<Flight, Integer> cFlightDuration;

    @FXML
    private TextField searchBar;

    @FXML
    private TableView<Flight> tableItems;

    /**
     * Initialization of the menuPilot window:
     * Hides some component so that the user has to first print elements
     * @param location base parameter when the initialize method is overrided
     * @param resources base parameter when the initialize method is overrided
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cId.setVisible(false);
        cMission.setVisible(false);
        cPilotId.setVisible(false);
        cFighterId.setVisible(false);
        cFighterModel.setVisible(false);
        cDateBegin.setVisible(false);
        cDateEnd.setVisible(false);
        cDateBack.setVisible(false);
        cFlightDuration.setVisible(false);
    }

    /**
     * Action of the Disconnect button:
     * Closes the current window and opens the last window (connexion menu mainMenu)
     */
    @FXML
    void btnDisconnect_action() {
        super.open_quit(btnDisconnect,"/vue/mainMenu.fxml");
    }

    /**
     * Action of the "Modify Password" button:
     * Opens the window modification password, and gives the userID of the current user connected
     */
    @FXML
    void btnPassword_action() {
        //this.open_quit_share(btnConnect, "/vue/menuOfficer.fxml", menuOfficerController, name);
        modifyPasswordController modifyPasswordController = new modifyPasswordController();
        this.open_share( "/vue/modifyPassword.fxml", modifyPasswordController, this.getUserId());
    }

    /**
     * Action of the button btnFlights:
     * Prints the flight history of the Pilot inside the table
     * Set the search option for the searchBar so that it corresponds to the Flight information
     * Prints a message in the case where the Pilot history is empty
     */
    @FXML
    void btnFlights_action(){
        cId.setVisible(true);
        cMission.setVisible(true);
        cPilotId.setVisible(true);
        cFighterId.setVisible(true);
        cFighterModel.setVisible(true);
        cDateBegin.setVisible(true);
        cDateEnd.setVisible(true);
        cDateBack.setVisible(true);
        cFlightDuration.setVisible(true);

        ObservableList<Flight> list = FXCollections.observableArrayList();
        list = Request.displayHistory(this.getUserId());
        if (!list.isEmpty()) {

            cId.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightID"));
            cMission.setCellValueFactory(new PropertyValueFactory<Flight, String>("missionName"));
            cPilotId.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("pilotID"));
            cFighterId.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("fighterID"));
            cFighterModel.setCellValueFactory(new PropertyValueFactory<Flight, TieModel>("fighterModel"));
            cDateBegin.setCellValueFactory(new PropertyValueFactory<Flight, String>("start"));
            cDateEnd.setCellValueFactory(new PropertyValueFactory<Flight, String>("endRent"));
            cDateBack.setCellValueFactory(new PropertyValueFactory<Flight, String>("endFlight"));
            cFlightDuration.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("flightDuration"));

            tableItems.setItems(list);

            FilteredList<Flight> filteredData = new FilteredList<Flight>(list, b -> true);
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(flight -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (String.valueOf(flight.getFlightID()).indexOf(lowerCaseFilter) != -1)
                        return true;
                    else if (flight.getMissionName().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else if (flight.getStart().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else if (flight.getEndFlight().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    else if (String.valueOf(flight.getPilotID()).indexOf(lowerCaseFilter) != -1)
                        return true;
                    else if (String.valueOf(flight.getFighterID()).indexOf(lowerCaseFilter) != -1)
                        return true;
                    else if (flight.getEndRent().toLowerCase().indexOf(lowerCaseFilter) != -1)
                        return true;
                    if (String.valueOf(flight.getFlightDuration()).indexOf(lowerCaseFilter) != -1)
                        return true;
                    else
                        return false;
                });
            });

            SortedList<Flight> sortedData = new SortedList<Flight>(filteredData);
            sortedData.comparatorProperty().bind(tableItems.comparatorProperty());
            tableItems.setItems(sortedData);
        }

        else{
            labelMessage.setText("You don't have any flights in your history.");
        }
    }



}
