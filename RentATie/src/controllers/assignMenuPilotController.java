package controllers;

import bdd.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import login.Pilot;
import status.PilotStatus;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controller for the choice of a Pilot inside the Assignment to a Flight
 * A table with all the Pilot available (those safe and not inflight) are displayed in the middle of this window
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class assignMenuPilotController extends Controller implements Initializable {


    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    private TableView<Pilot> tableItems;

    @FXML
    private TableColumn<Pilot, Integer> cAge;

    @FXML
    private TableColumn<Pilot, Integer> cId;

    @FXML
    private TableColumn<Pilot, Boolean> cInFlight;

    @FXML
    private TableColumn<Pilot, String> cName;

    @FXML
    private TableColumn<Pilot, Integer> cShipsDestroyed;

    @FXML
    private TableColumn<Pilot, PilotStatus> cStatus;

    @FXML
    private TableColumn<Pilot, Integer> cTotalFlight;

    @FXML
    private TextField searchBar;


    /**
     * When the window is initialised:
     * print all the Pilot available, generate search option for the search bar
     * @param location base parameter when the method initialize is overrided
     * @param resources base parameter when the method initialize is overrided
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Pilot> list = FXCollections.observableArrayList();
        list= Request.displayPilotAvailable();

        cId.setCellValueFactory(new PropertyValueFactory<Pilot,Integer>("pilotID"));
        cName.setCellValueFactory(new PropertyValueFactory<Pilot,String>("name"));
        cAge.setCellValueFactory(new PropertyValueFactory<Pilot,Integer>("age"));
        cInFlight.setCellValueFactory(new PropertyValueFactory<Pilot,Boolean>("inFlight"));
        cStatus.setCellValueFactory(new PropertyValueFactory<Pilot,PilotStatus>("status"));
        cTotalFlight.setCellValueFactory(new PropertyValueFactory<Pilot,Integer>("totalFlight"));
        cShipsDestroyed.setCellValueFactory(new PropertyValueFactory<Pilot,Integer>("shipDestroyed"));

        tableItems.setItems(list);

        FilteredList<Pilot> filteredData = new FilteredList<Pilot>(list, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(pilot -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(pilot.getPilotID()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else if (pilot.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                    return true;
                else if (String.valueOf(pilot.getAge()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else if (pilot.getStatus().toString().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                    return true;
                else if (String.valueOf(pilot.getTotalFlight()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else if (String.valueOf(pilot.getShipDestroyed()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false;
            });
        });

        SortedList<Pilot> sortedData = new SortedList<Pilot>(filteredData);
        sortedData.comparatorProperty().bind(tableItems.comparatorProperty());
        tableItems.setItems(sortedData);

    }

    /**
     * Button Cancel action:
     * Cancels the assignment, closes the window and returns to the initial menu (Officer menu)
     */
    @FXML
    void btnCancel_action() {
        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

    /**
     * Button Validate action:
     * Get the selected pilot by the user for the assignment to a Flight
     * take into account the case where user select nothing
     * Redirect to the next step of assignment (TieFighter selection) if the Pilot has been choosed correctly
     */
    @FXML
    void btnValidate_action() {
        //entrer dans bdd
        Pilot pilotSelected = (Pilot) tableItems.getSelectionModel().getSelectedItem();
        if(pilotSelected==null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to choose a Pilot to assign !");
            errorAlert.showAndWait();
        }

        else {
            assignMenuFighterController controller = new assignMenuFighterController();
            super.open_quit_share(btnValidate, "/vue/assignMenuFighter.fxml", controller, pilotSelected);
        }

    }


}
