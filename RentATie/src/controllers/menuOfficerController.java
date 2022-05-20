package controllers;

import bdd.Request;
import fly.Flight;
import fly.TieFighter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import login.Pilot;
import status.PilotStatus;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class menuOfficerController extends Controller implements Initializable {
    /*
     * A faire:
     *- bien refaire la recherche searchBar pr chaque objet (pas appliquer à chaque objet les trucs)
     * - les lectures de bdd
     * - manque des msg d'erreur qd on veut creer un flight en se trompant
     * */
    @FXML
    private Label labelUsername;


    @FXML
    private Button btnAdd;

    @FXML
    private Button btnAssign;

    @FXML
    private Button btnDisconnect;

    @FXML
    private Button btnPassword;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnModify;

    @FXML
    private TextField searchBar;

    @FXML
    private CheckBox cbFighter;

    @FXML
    private CheckBox cbFlight;

    @FXML
    private CheckBox cbPilot;

    @FXML
    private SplitMenuButton orderMenu;


    @FXML
    private TableView tableItems;

    @FXML
    private TableColumn<Object,Object> c1;
    //public TableColumn<> c1;

    @FXML
    private TableColumn<Object,Object> c2;
    //public TableColumn<> c2;

    @FXML
    private TableColumn<Object,Object> c3;
    //public TableColumn<> c3;

    @FXML
    private TableColumn<Object,Object> c4;
    //public TableColumn<> c4;

    @FXML
    private TableColumn<Object,Object> c5;
    //public TableColumn<> c5;

    @FXML
    private TableColumn<Object,Object> c6;
    //public TableColumn<> c6;

    @FXML
    private TableColumn<Object,Object> c7;
    //public TableColumn<> c7;

    @FXML
    private TableColumn<Object,Object> c8;
    //public TableColumn<> c8;

    private void test(){
        labelUsername.setText(getUserName());
        ObservableList<Objet1> list = FXCollections.observableArrayList(
                new Objet1(1),
                new Objet1(2),
                new Objet1(3),
                new Objet1(1),
                new Objet1(3),
                new Objet1(3),
                new Objet1(2),
                new Objet1(1),
                new Objet1(1),
                new Objet1(3),
                new Objet1(2),
                new Objet1(1),
                new Objet1(1),
                new Objet1(2),
                new Objet1(3),
                new Objet1(3),
                new Objet1(2),
                new Objet1(1),
                new Objet1(1)
                /*new  Objet2(),
                new  Objet2(),
                new  Objet2(),
                new  Objet2(),
                new  Objet2(),
                new  Objet2(),
                new  Objet2()*/
        );

        c1.setCellValueFactory(new PropertyValueFactory<Object,Object>("int1"));
        c2.setCellValueFactory(new PropertyValueFactory<Object,Object>("string1"));

        tableItems.setItems(list);

        FilteredList<Objet1> filteredData = new FilteredList<Objet1>(list, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(objet1 -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (String.valueOf(objet1.getInt1()).indexOf(lowerCaseFilter)!=-1)
                        return true;
                    else if (objet1.getString1().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                        return true;
                    }
                    else
                        return false;
                });
            });

        SortedList<Objet1> sortedData = new SortedList<Objet1>(filteredData);
        sortedData.comparatorProperty().bind(tableItems.comparatorProperty());
        tableItems.setItems(sortedData);

    }

    private void print_pilot(){
        //faire methode pour vider la bdd avant de re remplir
        PilotStatus ps1 = PilotStatus.Wounded;
        PilotStatus ps2 = PilotStatus.Safe;
        ObservableList<Pilot> list = FXCollections.observableArrayList();
        list= Request.displayPilotTable();

        c1.setCellValueFactory(new PropertyValueFactory<Object,Object>("pilotID"));
        c2.setCellValueFactory(new PropertyValueFactory<Object,Object>("name"));
        c3.setCellValueFactory(new PropertyValueFactory<Object,Object>("age"));
        c4.setCellValueFactory(new PropertyValueFactory<Object,Object>("inFlight"));
        c5.setCellValueFactory(new PropertyValueFactory<Object,Object>("status"));
        c6.setCellValueFactory(new PropertyValueFactory<Object,Object>("totalFlight"));
        c7.setCellValueFactory(new PropertyValueFactory<Object,Object>("shipDestroyed"));

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

    private void print_fighter(){
        //faire methode pour vider la bdd avant de re remplir
        ObservableList<TieFighter> list = FXCollections.observableArrayList();
        list=Request.displayTieFighterTable();

        c1.setCellValueFactory(new PropertyValueFactory<Object,Object>("fighterId"));
        c2.setCellValueFactory(new PropertyValueFactory<Object,Object>("model"));
        c3.setCellValueFactory(new PropertyValueFactory<Object,Object>("inFlight"));
        c4.setCellValueFactory(new PropertyValueFactory<Object,Object>("status"));


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


    private void print_flight(){
        //faire methode pour vider la bdd avant de re remplir
        ObservableList<Flight> list = FXCollections.observableArrayList();
        list = Request.displayFlightTable();

        c1.setCellValueFactory(new PropertyValueFactory<Object,Object>("flightID"));
        c2.setCellValueFactory(new PropertyValueFactory<Object,Object>("missionName"));
        c3.setCellValueFactory(new PropertyValueFactory<Object,Object>("pilotID"));
        c4.setCellValueFactory(new PropertyValueFactory<Object,Object>("fighterID"));
        c5.setCellValueFactory(new PropertyValueFactory<Object,Object>("fighterModel"));
        c6.setCellValueFactory(new PropertyValueFactory<Object,Object>("start"));
        c7.setCellValueFactory(new PropertyValueFactory<Object,Object>("endFlight"));
        c8.setCellValueFactory(new PropertyValueFactory<Object,Object>("endRent"));

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
    void cbFighter_checked() {



        c1.setText("id");
        c2.setText("model");
        c3.setText("inFlight");
        c4.setText("status");
        c5.setVisible(false);
        c6.setVisible(false);
        c7.setVisible(false);
        c8.setVisible(false);
        cbFighter.setSelected(true);
        cbFlight.setSelected(false);
        cbPilot.setSelected(false);
        btnAdd.setVisible(true);
        btnDelete.setVisible(false);

        print_fighter();
    }

    @FXML
    void cbFlight_checked() {

        //uncheck les autres
        c1.setText("id");
        c2.setText("mission");
        c3.setText("pilot-id");
        c4.setText("fighter-id");
        c5.setText("fighter");
        c6.setText("begin-date");
        c7.setText("end-date");
        c8.setText("back-date");
        c5.setVisible(true);
        c6.setVisible(true);
        c7.setVisible(true);
        c8.setVisible(true);
        cbFlight.setSelected(true);
        cbFighter.setSelected(false);
        cbPilot.setSelected(false);
        btnAdd.setVisible(false);
        btnDelete.setVisible(false);
        print_flight();
    }

    @FXML
    void cbPilot_checked() {

        //uncheck les autres
        c1.setText("id");
        c2.setText("nom");
        c3.setText("age");
        c4.setText("inFlight");
        c5.setText("status");
        c6.setText("total-flights");
        c7.setText("ship-destroyed");
        c5.setVisible(true);
        c6.setVisible(true);
        c7.setVisible(true);
        c8.setVisible(false);
        cbPilot.setSelected(true);
        cbFighter.setSelected(false);
        cbFlight.setSelected(false);
        btnAdd.setVisible(true);
        btnDelete.setVisible(true);
        print_pilot();
    }

    @FXML
    void btnAdd_action() {
        //selon les cb

        //if pilot
        Stage home = new Stage();
        if(cbPilot.isSelected()) {
            super.open_quit(btnAdd,"/vue/creationMenuPilot.fxml");
        }

        //if fighter
        else if(cbFighter.isSelected()) {
            super.open_quit(btnAdd,"/vue/creationMenuFighter.fxml");
        }

        else if(cbFlight.isSelected()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to assign if you want to create a flight !");
            errorAlert.showAndWait();
        }
        //if flight = mettre un msg d'erreur pour dire de faire assignment
    }

    @FXML
    void btnAssign_action() {
        super.open_quit(btnAssign,"/vue/assignMenuPilot.fxml");
    }

    @FXML
    void btnDisconnect_action() {
        super.open_quit(btnDisconnect,"/vue/mainMenu.fxml");
    }

    @FXML
    void btnPassword_action(){
        super.open("/vue/modifyPassword.fxml");
    }

    @FXML
    void btnDelete_action(){
        //BDD ENLEVER
        //REAFFICHER TOUT (pas sur)
        if(tableItems.getSelectionModel().getSelectedItem() == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to choose an element to erase !");
            errorAlert.showAndWait();
        }
        Objet1 test = (Objet1) tableItems.getSelectionModel().getSelectedItem();
        System.out.println(test);

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setHeaderText("Erase an element");
        confirmAlert.setContentText("Do you really want to erase this element ?");
        Optional<ButtonType> result=confirmAlert.showAndWait();
        if(result.get()==ButtonType.OK){
            System.out.println("\n c bon");
        }
        /*else{
            confirmAlert.close();
        }*/
    }

    @FXML
    void btnModify_action(){
        if(tableItems.getSelectionModel().getSelectedItem() == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to choose an element to modify !");
            errorAlert.showAndWait();
        }

        else if(cbPilot.isSelected()) {
            //caster en pilote
            Objet1 test = (Objet1) tableItems.getSelectionModel().getSelectedItem();
            System.out.println(test);
            super.open_quit(btnModify,"/vue/modifyMenuPilot.fxml");
        }

        else if(cbFlight.isSelected()) {
            //caster en flight
            Objet1 test = (Objet1) tableItems.getSelectionModel().getSelectedItem();
            System.out.println(test);
            super.open_quit(btnModify,"/vue/modifyMenuFlight.fxml");
        }

        else if(cbFighter.isSelected()) {
            //caster en fighter
            Objet1 test = (Objet1) tableItems.getSelectionModel().getSelectedItem();
            System.out.println(test);
            super.open_quit(btnModify,"/vue/modifyMenuFighter.fxml");
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("\nLe string :"+this.getUserName());
        //labelUsername.setText(getUserName());
    }
}
