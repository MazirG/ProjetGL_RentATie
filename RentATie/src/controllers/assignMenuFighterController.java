package controllers;

import bdd.Request;
import fly.TieFighter;
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
import status.FighterStatus;
import status.TieModel;

import java.net.URL;
import java.util.ResourceBundle;

public class assignMenuFighterController extends Controller implements Initializable {

    /*
     * A faire:
     * -ajouter la lecture bdd uniquement fighter en dispo et en etat
     * -les conditions pour selectionner item
     * -verifier que redirection fonctionne correctement avec objet transmis
     * */

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


    @FXML
    void btnCancel_action() {
        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");
        //ajouter remise a zero des champs pr datas de la bd
    }

    @FXML
    void btnValidate_action() {
        //entrer dans bdd
        super.open_quit(btnValidate,"/vue/creationMenuFlight.fxml");
        TieFighter fighterSelected = (TieFighter) tableItems.getSelectionModel().getSelectedItem();
        creationMenuFlightController controller = new creationMenuFlightController();
        super.open_quit_share(btnValidate,"/vue/creationMenuFlight.fxml",controller, fighterSelected);
    }



}
