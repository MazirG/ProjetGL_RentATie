package controllers;
import fly.FlightLate;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the print of Flight that are late
 * A table with all the Flights that have ending rent day anterior to today's date are displayed in the middle of this window
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class whosLateController extends Controller implements Initializable {
    @FXML
    private Button btnCancel;

    @FXML
    private TableColumn<FlightLate, String> cBeginRent;

    @FXML
    private TableColumn<FlightLate, String> cEndRent;

    @FXML
    private TableColumn<FlightLate, Integer> cId;

    @FXML
    private TableColumn<FlightLate, Integer> cLate;

    @FXML
    private Button btnPrint;

    @FXML
    private TableView<FlightLate> tableItems;

    @FXML
    private TextField searchBar;

    /**
     * Initialisation of the window:
     * Hides component so that the user has to first refresh the page to see componenents and functionalities
     * @param location base parameter of the initialize method when overrided
     * @param resources base parameter of the initialize method when overrided
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cId.setVisible(false);
        cLate.setVisible(false);
        cEndRent.setVisible(false);
        cBeginRent.setVisible(false);
    }


    /**
     * Action of the "Refresh" button:
     * Prints Flight that are late (with FlightLate objects) with the ObservableList transfered in the Officer menu
     * Changes the search options for the search bar so that it is adapted to the FlightLate information of the table
     */
    @FXML
    void btnPrint_action() {
        cId.setVisible(true);
        cLate.setVisible(true);
        cEndRent.setVisible(true);
        cBeginRent.setVisible(true);

        cId.setCellValueFactory(new PropertyValueFactory<FlightLate,Integer>("id"));
        cBeginRent.setCellValueFactory(new PropertyValueFactory<FlightLate,String>("begin"));
        cEndRent.setCellValueFactory(new PropertyValueFactory<FlightLate,String>("end"));
        cLate.setCellValueFactory(new PropertyValueFactory<FlightLate,Integer>("late"));

        tableItems.setItems(this.getListLate());

        FilteredList<FlightLate> filteredData = new FilteredList<FlightLate>(this.getListLate(), b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(flight -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(flight.getId()).indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (flight.getBegin().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (flight.getEnd().toLowerCase().indexOf(lowerCaseFilter) != -1)
                    return true;
                else if (String.valueOf(flight.getLate()).indexOf(lowerCaseFilter) != -1)
                    return true;

                else
                    return false;
            });
        });

        SortedList<FlightLate> sortedData = new SortedList<FlightLate>(filteredData);
        sortedData.comparatorProperty().bind(tableItems.comparatorProperty());
        tableItems.setItems(sortedData);
    }

    /**
     * Action for the Cancel button:
     * Closes the current window
     */
    @FXML
    void btnCancel() {
        quit(btnCancel);
    }



}
