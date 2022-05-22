package controllers;

import bdd.Request;
import fly.Flight;
import fly.FlightLate;
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

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Controller for the Officer menu
 * A table with all the Objects of the DB is available, it can display all the Pilots/Flights/TieFighter depending on the object selected
 * This menu offers a large panel of options depending on the object selected
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class menuOfficerController extends Controller implements Initializable {

    @FXML
    private Button btnLate;

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

    @FXML
    private TableColumn<Object,Object> c9;

    /**
     * Initialisation of the window that hides all the components/functionalities so that the user has to first print elements
     * @param location base parameter of the initialize method when overrided
     * @param resources base parameter of the initialize method when overrided
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        c1.setVisible(false);
        c2.setVisible(false);
        c3.setVisible(false);
        c4.setVisible(false);
        c5.setVisible(false);
        c6.setVisible(false);
        c7.setVisible(false);
        c8.setVisible(false);
        c9.setVisible(false);
        btnLate.setVisible(false);
        cbFlight.setSelected(false);
        cbFighter.setSelected(false);
        cbPilot.setSelected(false);
        btnAdd.setVisible(false);
        btnDelete.setVisible(false);
        btnModify.setVisible(false);
        btnAssign.setVisible(false);
    }


    /**
     * Method that allows to print all the Pilot inside the table of objects
     * Modifies the search options for the search bar so that it is adapted to the Pilot information
     */
    private void print_pilot(){

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

    /**
     * Method that allows to print all the TieFighter inside the table of objects
     * Modifies the search options for the search bar so that it is adapted to the TieFighter information
     */
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

    /**
     * Method that allows to print all the Flight inside the table of objects
     * Modifies the search options for the search bar so that it is adapted to the Flight information
     */
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
        c7.setCellValueFactory(new PropertyValueFactory<Object,Object>("endRent"));
        c8.setCellValueFactory(new PropertyValueFactory<Object,Object>("endFlight"));
        c9.setCellValueFactory(new PropertyValueFactory<Object,Object>("flightDuration"));
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
                else if (String.valueOf(flight.getPilotID()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else if (String.valueOf(flight.getFighterID()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else if (flight.getEndRent().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                    return true;
                else if (String.valueOf(flight.getFlightDuration()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false;
            });
        });

        SortedList<Flight> sortedData = new SortedList<Flight>(filteredData);
        sortedData.comparatorProperty().bind(tableItems.comparatorProperty());
        tableItems.setItems(sortedData);
    }


    /**
     * Action of the radioButton cbFighter:
     * Allows to select the objecct TieFighter, and set the print to TieFighter elements
     * Prints all the components for the action that can be done with TieFIghter elements
     */
    @FXML
    void cbFighter_checked() {

        c1.setText("id");
        c2.setText("model");
        c3.setText("inFlight");
        c4.setText("status");
        c1.setVisible(true);
        c2.setVisible(true);
        c3.setVisible(true);
        c4.setVisible(true);
        c5.setVisible(false);
        c6.setVisible(false);
        c7.setVisible(false);
        c8.setVisible(false);
        c9.setVisible(false);
        btnLate.setVisible(false);
        cbFighter.setSelected(true);
        cbFlight.setSelected(false);
        cbPilot.setSelected(false);
        btnAdd.setVisible(true);
        btnDelete.setVisible(false);
        btnModify.setVisible(true);
        btnAssign.setVisible(true);

        print_fighter();
    }

    /**
     * Action of the radioButton cbFlight
     * Allows to select the object Flight, and set the print to Flight elements
     * Prints all the components for the action that can be done with Flight elements
     */
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
        c9.setText("duration(day)");

        c1.setVisible(true);
        c2.setVisible(true);
        c3.setVisible(true);
        c4.setVisible(true);
        c5.setVisible(true);
        c6.setVisible(true);
        c7.setVisible(true);
        c8.setVisible(true);
        c9.setVisible(true);
        cbFlight.setSelected(true);
        cbFighter.setSelected(false);
        cbPilot.setSelected(false);
        btnAdd.setVisible(false);
        btnDelete.setVisible(false);
        btnLate.setVisible(false);
        btnModify.setVisible(true);
        btnAssign.setVisible(true);
        print_flight();

        ObservableList<FlightLate> list = FXCollections.observableArrayList();
        this.setListLate(list);
        for(Object flight:tableItems.getItems()) {
            if (flight instanceof Flight) {
                LocalDate dateToday = LocalDate.parse(String.valueOf(LocalDate.now()));
                LocalDate dateEnd = LocalDate.parse(((Flight) flight).getEndRent());
                String dateBack = ((Flight) flight).getEndFlight();
                if (dateEnd.compareTo(dateToday) < 0 && dateBack == null) {
                    this.getListLate().add(new FlightLate((Flight) flight));
                }
            }
        }
        if(!this.getListLate().isEmpty()){
            btnLate.setVisible(true);
        }
    }


    /**
     * Action of the radioButton cbPilot
     * Allows to select the object Pilot, and set the print to Pilot elements
     * Prints all the components for the action that can be done with Pilot elements
     */
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
        c1.setVisible(true);
        c2.setVisible(true);
        c3.setVisible(true);
        c4.setVisible(true);
        c5.setVisible(true);
        c6.setVisible(true);
        c7.setVisible(true);
        c8.setVisible(false);
        c9.setVisible(false);
        cbPilot.setSelected(true);
        cbFighter.setSelected(false);
        cbFlight.setSelected(false);
        btnAdd.setVisible(true);
        btnDelete.setVisible(true);
        btnModify.setVisible(true);
        btnLate.setVisible(false);
        btnAssign.setVisible(true);
        print_pilot();
    }

    /**
     * Action of the button "See flights that are late":
     * Opens the window whosLate that prints all the Flight that are late, by giving the list of Flight that are late
     */
    @FXML
    public void btnLate_action(){
        whosLateController controller = new whosLateController();
        open_share("/vue/whosLate.fxml",controller,this.getListLate());
    }

    /**
     * Action of the "Add element" button:
     * Clickable only if the object chosen is Pilot or TieFighter
     * opens the creation menu Pilot if a Pilot is choosen
     * opens the creation menu TieFighter if a TieFighter is choosen
     */
    @FXML
    void btnAdd_action() {
        Stage home = new Stage();
        if(cbPilot.isSelected()) {
            super.open_quit(btnAdd,"/vue/creationMenuPilot.fxml");
        }

        else if(cbFighter.isSelected()) {
            super.open_quit(btnAdd,"/vue/creationMenuFighter.fxml");
        }

        else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to select Pilots or Fighters !");
            errorAlert.showAndWait();
        }
    }

    /**
     * Action of the "Assign element" button:
     * Closes the current window and opens the first assignment window for a Flight
     */
    @FXML
    void btnAssign_action() {
        super.open_quit(btnAssign,"/vue/assignMenuPilot.fxml");
    }

    /**
     * Action of the "Disconnect" button:
     * Closes the current window and opens the last window (connexion menu meinMenu)
     */
    @FXML
    void btnDisconnect_action() {
        super.open_quit(btnDisconnect,"/vue/mainMenu.fxml");
    }

    /**
     * Ouvre la fenetre de modification de mot de passe en transmettant l'ID de l'utilisateur actuellement connecté
     * Opens the modify password window and gives the ID of the current user connected
     */
    @FXML
    void btnPassword_action(){
        modifyPasswordController modifyPasswordController = new modifyPasswordController();
        this.open_share("/vue/modifyPassword.fxml", modifyPasswordController, this.getUserId());
    }

    /**
     * Action of the "Delete User" button:
     * Clickable only if object chosen is Pilot
     * Gets the Pilot selected by the user and delete his access rights to the application (delete his username/password in DB)
     * Takes in account the case where Pilot is in a Flight, case where Pilot is already delete, case where nothing is selected, case where SQL request has failed
     */
    @FXML
    void btnDelete_action(){
        Pilot pilotSelected = (Pilot)tableItems.getSelectionModel().getSelectedItem();
        int sqlResult = -1;
        if(pilotSelected == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to choose an element to erase !");
            errorAlert.showAndWait();
        }
        if(pilotSelected.isInFlight()==true){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You can't erase a pilot in a Flight !");
            errorAlert.showAndWait();
        }
        else{
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setHeaderText("Erase an element");
            confirmAlert.setContentText("Do you really want to erase this element ?");
            Optional<ButtonType> result=confirmAlert.showAndWait();
            if(result.get()==ButtonType.OK){
                boolean succeed = true;
                try{
                    sqlResult=Request.deleteUser(pilotSelected.getPilotID());
                }catch (Exception e){
                    succeed=false;
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Error");
                    errorAlert.setContentText("Request has failed !");
                    errorAlert.showAndWait();
                }
                if(succeed && sqlResult==1){
                    Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                    errorAlert.setContentText("The Pilot has been removed !");
                    errorAlert.show();
                }

                else if(succeed && sqlResult==0){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Error");
                    errorAlert.setContentText("Pilot already removed !");
                    errorAlert.showAndWait();
                }

            }
        }
    }

    /**
     * Action of the "Modify" button:
     * Gets the object selected by the user
     * opens the modification menu of Pilot if a Pilot is chosen and transfers the Pilot chosen
     * opens the modification menu of TieFighter if a TieFighter is chosen and transfers the TieFighter chosen
     * opens the modification menu of Flight if a Flight is chosen and transfers the Flight chosen
     *
     */
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
            Pilot pilotSelected = (Pilot) tableItems.getSelectionModel().getSelectedItem();
            if(pilotSelected.isInFlight()==true){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("You can't modify a pilot in flight !");
                errorAlert.showAndWait();
            }
            else{
                //System.out.println("\n"+pilotSelected);
                modifyMenuPilotController modifyMenuPilotController = new modifyMenuPilotController();
                this.open_quit_share(btnModify, "/vue/modifyMenuPilot.fxml", modifyMenuPilotController, pilotSelected);
            }

        }

        else if(cbFlight.isSelected()) {
            //caster en flight
            Flight flightSelected = (Flight) tableItems.getSelectionModel().getSelectedItem();
            if(flightSelected.getEndFlight()!=null){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("You can't modify a Flight twice !");
                errorAlert.showAndWait();
            }
            else {
                //System.out.println("\n"+flightSelected);
                modifyMenuFlightController modifyMenuFlightController = new modifyMenuFlightController();
                this.open_quit_share(btnModify, "/vue/modifyMenuFlight.fxml", modifyMenuFlightController, flightSelected);
            }

        }

        else if(cbFighter.isSelected()) {
            //caster en fighter
            TieFighter fighterSelected = (TieFighter) tableItems.getSelectionModel().getSelectedItem();
            if(fighterSelected.isInFlight()==true){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("You can't modify a tie in flight !");
                errorAlert.showAndWait();
            }
            else{
                //System.out.println("\n"+fighterSelected);
                modifyMenuFighterController modifyMenuFighterController = new modifyMenuFighterController();
                this.open_quit_share(btnModify, "/vue/modifyMenuFighter.fxml", modifyMenuFighterController, fighterSelected);
            }

        }


    }



}
