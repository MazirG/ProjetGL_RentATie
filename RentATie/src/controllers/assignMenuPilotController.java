package controllers;

import bdd.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import login.Pilot;
import status.PilotStatus;

import java.net.URL;
import java.util.ResourceBundle;

public class assignMenuPilotController extends Controller implements Initializable {

    /*
    * A faire:
    * -lecture bdd seulement pilote dispos et qui peuvent conduire
    * -condition de selection
    * -verifier que redirection fonctionne correctement avec objet transmis
    * */


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

    @FXML
    void btnCancel_action() {
        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");
        //ajouter remise a zero des champs pr datas de la bd
    }

    @FXML
    void btnValidate_action() {
        //entrer dans bdd
        super.open_quit(btnValidate,"/vue/assignMenuFighter.fxml");
        /*Pilot pilotSelected = (Pilot) tableItems.getSelectionModel().getSelectedItem();
        assignMenuFighterController controller = new assignMenuFighterController();
        super.open_quit_share(btnValidate,"/vue/assignMenuFighter.fxml",controller, pilotSelected);*/


    }


}
