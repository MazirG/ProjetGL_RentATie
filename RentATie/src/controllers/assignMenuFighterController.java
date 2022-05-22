package controllers;

import bdd.Request;
import fly.TieFighter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import status.FighterStatus;
import status.TieModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the choice of a TIeFighter inside the Assignment to a Flight
 * A table with all the tieighter available (those functional and not in flight) are displayed in the middle of this window
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class assignMenuFighterController extends Controller implements Initializable {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    private TableView<TieFighter> tableItems;

    @FXML
    private TableColumn<TieFighter, Integer> cId;

    @FXML
    private TableColumn<TieFighter, Boolean> cInFlight;

    @FXML
    private TableColumn<TieFighter, TieModel> cModel;

    @FXML
    private TableColumn<TieFighter, FighterStatus> cStatus;

    @FXML
    private TextField searchBar;

    /**
     * At the initialization of the window:
     * print all the Tie available for a flight and generate the search conditions for search bar
     * @param location base parameter when the method initialize is overrided
     * @param resources base parameter when the method initialize is overrided
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<TieFighter> list = FXCollections.observableArrayList();
        list= Request.displayTieFighterAvailable();

        cId.setCellValueFactory(new PropertyValueFactory<TieFighter,Integer>("fighterId"));
        cModel.setCellValueFactory(new PropertyValueFactory<TieFighter, TieModel>("model"));
        cInFlight.setCellValueFactory(new PropertyValueFactory<TieFighter,Boolean>("inFlight"));
        cStatus.setCellValueFactory(new PropertyValueFactory<TieFighter, FighterStatus>("status"));


        tableItems.setItems(list);

        FilteredList<TieFighter> filteredData = new FilteredList<TieFighter>(list, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(fighter -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(fighter.getFighterId()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else if (fighter.getModel().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                    return true;
                else if (fighter.getStatus().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                    return true;

                else
                    return false;
            });
        });

        SortedList<TieFighter> sortedData = new SortedList<TieFighter>(filteredData);
        sortedData.comparatorProperty().bind(tableItems.comparatorProperty());
        tableItems.setItems(sortedData);
    }

    /**
     * Action of the Cancel button:
     * Cancel assignment, close window en return to the initial window (Officer menu)
     */
    @FXML
    void btnCancel_action() {
        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

    /**
     * Action for the Validate button:
     * Get the object where the user clicked, and assign it to a flight
     * take into account case where user select nothing
     * redirect to the nexy step of assignment (Flight creation) once the Fighter is selected
     */
    @FXML
    void btnValidate_action() {
        TieFighter fighterSelected = (TieFighter) tableItems.getSelectionModel().getSelectedItem();
        if(fighterSelected == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to choose a Tie to assign !");
            errorAlert.showAndWait();
        }
        else {
            creationMenuFlightController controller = new creationMenuFlightController();
            super.open_quit_share(btnValidate,"/vue/creationMenuFlight.fxml",controller, fighterSelected);
        }
    }



}
