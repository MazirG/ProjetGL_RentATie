package controllers;

import bdd.Request;
import fly.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import status.TieModel;

import java.net.URL;
import java.util.ResourceBundle;

public class menuPilotController extends Controller implements Initializable {
    /*
     * A faire:
     * tout mais comme assignment
     * */
    @FXML
    private Button btnDisconnect;

    @FXML
    private Button btnPassword;

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
    private TextField searchBar;

    @FXML
    private TableView<Flight> tableItems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Flight> list = FXCollections.observableArrayList();
        //modifier le type de la requete
        list = Request.displayFlightTable();

        cId.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("flightID"));
        cMission.setCellValueFactory(new PropertyValueFactory<Flight,String>("missionName"));
        cPilotId.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("pilotID"));
        cFighterId.setCellValueFactory(new PropertyValueFactory<Flight,Integer>("fighterID"));
        cFighterModel.setCellValueFactory(new PropertyValueFactory<Flight,TieModel>("fighterModel"));
        cDateBegin.setCellValueFactory(new PropertyValueFactory<Flight,String>("start"));
        cDateEnd.setCellValueFactory(new PropertyValueFactory<Flight,String>("endFlight"));
        cDateBack.setCellValueFactory(new PropertyValueFactory<Flight,String>("endRent"));

        tableItems.setItems(list);

        FilteredList<Flight> filteredData = new FilteredList<Flight>(list, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(flight -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(flight.getFlightID()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else if (flight.getMissionName().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                    return true;
                else if (flight.getStart().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                    return true;
                else if (flight.getEndFlight().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                    return true;
                if (String.valueOf(flight.getPilotID()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                if (String.valueOf(flight.getFighterID()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else if (flight.getEndRent().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                    return true;
                else
                    return false;
            });
        });

        SortedList<Flight> sortedData = new SortedList<Flight>(filteredData);
        sortedData.comparatorProperty().bind(tableItems.comparatorProperty());
        tableItems.setItems(sortedData);
    }


    @FXML
    void btnDisconnect_action(ActionEvent event) {

    }

    @FXML
    void btnPassword_action(ActionEvent event) {

    }


}
