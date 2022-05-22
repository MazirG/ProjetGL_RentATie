package controllers;
import bdd.Request;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import status.FighterStatus;
import status.PilotStatus;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
/**
 * Controller for the modification of a Flight
 * All the fields inside the Flight that will be modified will need inputs from the user
 * The others non-editable datas are only displayed in the menu
 * Modifying a Flight means put an end to the flight (it can't be modified twice)
 * The user enters the results of the Flight: new Pilot status, new TieFighter status, number of enemy ships destroyed
 * The date back for the Fighter is automatically set at today's system date
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class modifyMenuFlightController extends Controller implements Initializable {
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnValidate;

    @FXML
    private Label labelBeginDate;

    @FXML
    private Label labelDateBack;

    @FXML
    private Label labelEndDate;

    @FXML
    private Label labelFighterID;

    @FXML
    private Label labelFlightID;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPilotID;

    @FXML
    private RadioButton rbDamaged;

    @FXML
    private RadioButton rbDead;

    @FXML
    private RadioButton rbDestroyed;

    @FXML
    private RadioButton rbFunctional;

    @FXML
    private RadioButton rbLost;

    @FXML
    private RadioButton rbSafe;

    @FXML
    private RadioButton rbWounded;

    @FXML
    private RadioButton rbWrecked;

    @FXML
    private TextField tfShips;

    private String pilotStatus="";
    private String fighterStatus="";

    /**
     * Setter for pilotStats
     * @param pilotStatus Pilot status after the flight
     */
    public void setPilotStatus(String pilotStatus) {
        this.pilotStatus = pilotStatus;
    }

    /**
     * Setter for fighterStatus
     * @param fighterStatus Fighter status after the flight
     */
    public void setFighterStatus(String fighterStatus) {
        this.fighterStatus = fighterStatus;
    }

    /**
     * Getter for pilotStatus
     * @return Pilot status after the flight
     */
    public String getPilotStatus() {
        return pilotStatus;
    }

    /**
     * Getter for fighterStatus
     * @return Fighter status after the flight
     */
    public String getFighterStatus() {
        return fighterStatus;
    }

    /**
     * Initialisation of the window:
     * Hides component so that the user has to first refresh the page to see componenents and functionalities
     * @param location base parameter of the initialize method when overrided
     * @param resources base parameter of the initialize method when overrided
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnValidate.setVisible(false);
        tfShips.setVisible(false);
        labelPilotID.setVisible(false);
        labelFlightID.setVisible(false);
        labelFighterID.setVisible(false);
        labelBeginDate.setVisible(false);
        labelEndDate.setVisible(false);
        labelDateBack.setVisible(false);
        labelName.setVisible(false);
        rbDamaged.setVisible(false);
        rbDestroyed.setVisible(false);
        rbFunctional.setVisible(false);
        rbDead.setVisible(false);
        rbWrecked.setVisible(false);
        rbLost.setVisible(false);
        rbSafe.setVisible(false);
        rbWounded.setVisible(false);
    }

    /**
     * Action of the Refresh button:
     * Prints all the components of the winsow, the functionalities available, and the actual Flight information of the Flight
     */
    @FXML
    void btnRefresh_action() {
        btnValidate.setVisible(true);
        btnCancel.setVisible(true);
        tfShips.setVisible(true);
        labelPilotID.setVisible(true);
        labelFlightID.setVisible(true);
        labelFighterID.setVisible(true);
        labelBeginDate.setVisible(true);
        labelEndDate.setVisible(true);
        labelDateBack.setVisible(true);
        labelName.setVisible(true);
        rbDamaged.setVisible(true);
        rbDestroyed.setVisible(true);
        rbFunctional.setVisible(true);
        rbDead.setVisible(true);
        rbWrecked.setVisible(true);
        rbLost.setVisible(true);
        rbSafe.setVisible(true);
        rbWounded.setVisible(true);
        labelPilotID.setText( String.valueOf( this.getFlight().getPilotID() ));
        labelName.setText(this.getFlight().getMissionName());
        labelBeginDate.setText(this.getFlight().getStart());
        labelEndDate.setText(this.getFlight().getEndRent());
        labelDateBack.setText(String.valueOf(LocalDate.now()));
        labelFlightID.setText(String.valueOf( this.getFlight().getFlightID() ));
        labelFighterID.setText(String.valueOf( this.getFlight().getFighterID() ));

    }

    /**
     * Action of the Cancel button:
     * Closes the actual window and opens the last window (menu Officer)
     */
    @FXML
    void btnCancel_action() {
        open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

    /**
     * Action of the Validate button:
     * Gets all the information entered by the user and updates the datainside the DB with an SQL request
     * Gives by the default the system day as a date for the end of the flight
     * Takes in account cases where new status are not selected, and case where SQL request fails
     * Case where the number of shipsDestroyed contains letter/is negative, case where modification is done before the date begin of the rent
     */

    @FXML
    void btnValidate_action() {
        int shipDestroyed = -1;
        LocalDate dateBegin = LocalDate.parse(labelBeginDate.getText());
        LocalDate dateBack = LocalDate.parse(labelDateBack.getText());
        if( ( pilotStatus.equals("Wounded")==false && pilotStatus.equals("Dead")==false && pilotStatus.equals("Lost")==false && pilotStatus.equals("Safe")==false )){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to chose a Pilot status to modify !");
            errorAlert.showAndWait();
        }
        else if(fighterStatus.equals("Wrecked")==false && fighterStatus.equals("Functional")==false && fighterStatus.equals("Destroyed")==false && fighterStatus.equals("Damaged")==false){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to chose a Fighter status to modify !");
            errorAlert.showAndWait();
        }

        else if(dateBack.compareTo(dateBegin)<0){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("It's to early to modify this flight !");
            errorAlert.showAndWait();
        }

        else{

            boolean estNbre = true;
            try{
                shipDestroyed=Integer.parseInt(tfShips.getText());
            }catch (NumberFormatException e){
                estNbre=false;
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("You need to enter a number to modify the data !");
                errorAlert.showAndWait();
            }

            if(estNbre && shipDestroyed>=0){
                boolean succeed = true;
                try{
                    Request.flightDone(this.getFlight().getFlightID(), PilotStatus.valueOf(pilotStatus),FighterStatus.valueOf(fighterStatus),shipDestroyed);
                }catch (Exception e){
                    succeed=false;
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Error");
                    errorAlert.setContentText("The request has failed !");
                    errorAlert.showAndWait();
                }

                if(succeed){
                    super.open_quit(btnValidate,"/vue/menuOfficer.fxml");
                    Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                    errorAlert.setContentText("The Flight has been modified !");
                    errorAlert.show();
                }


            }
            else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("The number must be positive !");
                errorAlert.showAndWait();
            }

        }
    }


    /**
     * Action of the radioButton rbDamaged:
     * Change the status of the Fighter to Damaged
     */
    @FXML
    void rbDamaged_action() {
        this.setFighterStatus("Damaged");
        rbDamaged.setSelected(true);
        rbWrecked.setSelected(false);
        rbDestroyed.setSelected(false);
        rbFunctional.setSelected(false);
    }

    /**
     * Action of the radioButton rbDead:
     * Change the future status of the Pilot to Dead
     */
    @FXML
    void rbDead_action() {
        this.setPilotStatus("Dead");
        rbLost.setSelected(false);
        rbWounded.setSelected(false);
        rbSafe.setSelected(false);
        rbDead.setSelected(true);
    }
    /**
     * Action of the radioButton rbDestroyed:
     * Change the status of the Fighter to Destroyed
     */
    @FXML
    void rbDestroyed_action() {
        this.setFighterStatus("Destroyed");
        rbDestroyed.setSelected(true);
        rbWrecked.setSelected(false);
        rbDamaged.setSelected(false);
        rbFunctional.setSelected(false);
    }

    /**
     * Action of the radioButton rbFunctional:
     * Change the status of the Fighter to Functional
     */
    @FXML
    void rbFunctional_action() {
        this.setFighterStatus("Functional");
        rbFunctional.setSelected(true);
        rbWrecked.setSelected(false);
        rbDestroyed.setSelected(false);
        rbDamaged.setSelected(false);
    }

    /**
     * Action of the radioButton rbLost:
     * Change the future status of the Pilot to Lost
     */
    @FXML
    void rbLost_action() {
        this.setPilotStatus("Lost");
        rbWounded.setSelected(false);
        rbDead.setSelected(false);
        rbSafe.setSelected(false);
        rbLost.setSelected(true);
    }

    /**
     * Action of the radioButton rbSafe:
     * Change the future status of the Pilot to Safe
     */
    @FXML
    void rbSafe_action() {
        this.setPilotStatus("Safe");
        rbWounded.setSelected(false);
        rbDead.setSelected(false);
        rbLost.setSelected(false);
        rbSafe.setSelected(true);
    }

    /**
     * Action of the radioButton rbWounded:
     * Change the future status of the Pilot to Wounded
     */
    @FXML
    void rbWounded_action() {
        this.setPilotStatus("Wounded");
        rbLost.setSelected(false);
        rbDead.setSelected(false);
        rbSafe.setSelected(false);
        rbWounded.setSelected(true);
    }

    /**
     * Action of the radioButton rbWrecked:
     * Change the status of the Fighter to Wrecked
     */
    @FXML
    void rbWrecked_action() {
        this.setFighterStatus("Wrecked");
        rbWrecked.setSelected(true);
        rbDamaged.setSelected(false);
        rbDestroyed.setSelected(false);
        rbFunctional.setSelected(false);
    }


}
