package controllers;
import bdd.Request;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import status.PilotStatus;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controller for the modification of a Pilot
 * All the fields inside the Pilot that will be modified will need inputs from the user
 * The others non-editable data are only displayed in the menu
 * The user enters the status and the Pilot age to modify
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class modifyMenuPilotController extends Controller implements Initializable {

    @FXML
    private Label labelFlights;

    @FXML
    private Label labelID;

    @FXML
    private Label labelName;

    @FXML
    private Label labelShipsDestroyed;

    @FXML
    private Label labelInFlight;

    @FXML
    private Button btnValidate;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnCancel;

    @FXML
    private RadioButton rbDead;

    @FXML
    private RadioButton rbLost;



    @FXML
    private RadioButton rbSafe;

    @FXML
    private RadioButton rbWounded;




    @FXML
    private TextField tfAge;



    //private int inFlight = -1;
    private String status="";

    /**
     * Initialisation of the window:
     * Hides component so that the user has to first refresh the page to see componenents and functionalities
     * @param location base parameter of the initialize method when overrided
     * @param resources base parameter of the initialize method when overrided
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelID.setVisible(false);
        labelShipsDestroyed.setVisible(false);
        labelFlights.setVisible(false);
        labelName.setVisible(false);
        labelInFlight.setVisible(false);
        tfAge.setVisible(false);
        rbDead.setVisible(false);
        rbSafe.setVisible(false);
        rbLost.setVisible(false);
        rbWounded.setVisible(false);
        btnValidate.setVisible(false);

    }

    /**
     * Action of the Refresh button:
     * Prints all the components of the winsow, the functionalities available, and the actual Flight information of the Flight
     */
    public void btnRefresh_action(){
        labelID.setVisible(true);
        labelShipsDestroyed.setVisible(true);
        labelFlights.setVisible(true);
        labelName.setVisible(true);
        labelInFlight.setVisible(true);
        tfAge.setVisible(true);
        rbDead.setVisible(true);
        rbSafe.setVisible(true);
        rbLost.setVisible(true);
        rbWounded.setVisible(true);
        btnValidate.setVisible(true);
        btnCancel.setVisible(true);
        labelID.setText( String.valueOf( this.getPilot().getId()) );
        labelFlights.setText( String.valueOf( this.getPilot().getTotalFlight()));
        labelShipsDestroyed.setText( String.valueOf( this.getPilot().getTotalFlight()));
        labelName.setText(this.getPilot().getName());
        tfAge.setText(String.valueOf( this.getPilot().getAge()));
    }

    /**
     * Setter for the future status of the Pilot
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter for the future status
     * @return the status
     */
    public String getStatus() {
        return status;
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
     * Action for the Validate button:
     * Gets the new status of the pilot and the new age, and updates the Pilot's information with an SQL request
     * Takes in account the cases when fields are empty/contain spaces, case when status not chosen, case when age is negative/not a number
     */
    @FXML
    void btnValidate_action() {
        int age = -1;
        boolean estNbre = true;
        if( ( status.equals("Wounded")==false && status.equals("Dead")==false && status.equals("Lost")==false && status.equals("Safe")==false )){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to chose physical status to modify !");
            errorAlert.showAndWait();
        }

        else{

            try{
                age=Integer.parseInt(tfAge.getText());
            }catch (NumberFormatException e){
                estNbre=false;
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("You need to enter a number to modify the data !");
                errorAlert.showAndWait();
            }

        }
        if(estNbre && age>0) {
            boolean succeed = true;
            try {
                Request.modifyPilotAge(this.getPilot().getPilotID(), age);
                Request.modifyPilotStatus(this.getPilot().getPilotID(), PilotStatus.valueOf(this.getStatus()));
            } catch (Exception e){
                succeed=false;
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("The request has failed !");
                errorAlert.showAndWait();
            }
            if(succeed){
                super.open_quit(btnValidate,"/vue/menuOfficer.fxml");
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setContentText("The Pilot has been modified !");
                errorAlert.show();

            }
        }
        else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to enter a positive number !");
            errorAlert.showAndWait();
        }
    }



    /**
     * Action of the radioButton rbLost:
     * Change the future status of the Pilot to Lost
     */
    @FXML
    void rbLost_action() {
        setStatus("Lost");
        rbWounded.setSelected(false);
        rbDead.setSelected(false);
        rbSafe.setSelected(false);
        rbLost.setSelected(true);

    }

    /**
     * Action of the radioButton rbWounded:
     * Change the future status of the Pilot to Wounded
     */
    @FXML
    void rbWounded_action() {
        setStatus("Wounded");
        rbLost.setSelected(false);
        rbDead.setSelected(false);
        rbSafe.setSelected(false);
        rbWounded.setSelected(true);
    }

    /**
     * Action of the radioButton rbSafe:
     * Change the future status of the Pilot to Safe
     */
    @FXML
    void rbSafe_action() {
        setStatus("Safe");
        rbWounded.setSelected(false);
        rbDead.setSelected(false);
        rbLost.setSelected(false);
        rbSafe.setSelected(true);
    }

    /**
     * Action of the radioButton rbDead:
     * Change the future status of the Pilot to Dead
     */
    @FXML
    void rbDead_action() {
        setStatus("Dead");
        rbLost.setSelected(false);
        rbWounded.setSelected(false);
        rbSafe.setSelected(false);
        rbDead.setSelected(true);

    }


}
