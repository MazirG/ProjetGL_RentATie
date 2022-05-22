package controllers;

import bdd.Request;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import status.FighterStatus;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Controller for the modification of a TieFighter
 * All the fields inside the TieFighter that will be modified will need inputs from the user
 * The others non-editable data are only displayed in the menu
 * The user enters the status to modify
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class modifyMenuFighterController extends Controller implements Initializable {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnValidate;

    @FXML
    private Label labelID;

    @FXML
    private Label labelInFlight;

    @FXML
    private Label labelModel;

    @FXML
    private RadioButton rbDamaged;

    @FXML
    private RadioButton rbDestroyed;

    @FXML
    private RadioButton rbFunctional;

    @FXML
    private RadioButton rbWreck;

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
        labelInFlight.setVisible(false);
        labelModel.setVisible(false);
        rbWreck.setVisible(false);
        rbFunctional.setVisible(false);
        rbDestroyed.setVisible(false);
        rbDamaged.setVisible(false);
        btnValidate.setVisible(false);
        //btnCancel.setVisible(false);

    }

    /**
     * Action of the Refresh button:
     * Prints all the elements of the window and the fonctionalities availables, and also the actuals data already inside the Fighter chosen
     */
    @FXML
    public void btnRefresh_action(){
        labelID.setVisible(true);
        labelInFlight.setVisible(true);
        labelModel.setVisible(true);
        rbWreck.setVisible(true);
        rbFunctional.setVisible(true);
        rbDestroyed.setVisible(true);
        rbDamaged.setVisible(true);
        btnValidate.setVisible(true);
        btnCancel.setVisible(true);
        labelID.setText( String.valueOf( this.getTieFighter().getFighterId()) );
        labelModel.setText(this.getTieFighter().getModel().toString());

    }

    /**
     * Getter for the status of the Fighter that will be modified
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter for the status of the Fighter that will be modified
     * @param status the new status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Action of the Cancel button:
     * Cancels the modifications, closes the current window and open the last window (menu Officer)
     */
    @FXML
    void btnCancel_action() {
        open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

    /**
     * Action of the Validate button:
     * Gets the new Flight status et modified the flight with an SQL request
     * Takes in account the case where the status is not selected and when SQL fails
     */
    @FXML
    void btnValidate_action() {
        if(status.equals("Wrecked")==false && status.equals("Functional")==false && status.equals("Destroyed")==false && status.equals("Damaged")==false){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to chose a status to modify !");
            errorAlert.showAndWait();
        }
        else{
            boolean succeed = true;
            try {
                Request.modifyFighterStatus(this.getTieFighter().getFighterId(), FighterStatus.valueOf(this.getStatus()));
            }catch (Exception e){
                succeed=false;
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("Request has failed !");
                errorAlert.showAndWait();
            }
            if(succeed){
                super.open_quit(btnValidate,"/vue/menuOfficer.fxml");
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setContentText("The Fighter has been modified !");
                errorAlert.show();
            }
        }
    }

    /**
     * Action of the radioButton rbDamaged:
     * Change the status selected to Damaged
     */
    @FXML
    void rbDamaged_action() {
        setStatus("Damaged");
        rbDamaged.setSelected(true);
        rbWreck.setSelected(false);
        rbDestroyed.setSelected(false);
        rbFunctional.setSelected(false);
    }

    /**
     * Action of the radioButton rbDestroyed:
     * Change the status selected to Destroyed
     */
    @FXML
    void rbDestroyed_action() {
        setStatus("Destroyed");
        rbDestroyed.setSelected(true);
        rbWreck.setSelected(false);
        rbDamaged.setSelected(false);
        rbFunctional.setSelected(false);
    }

    /**
     * Action of the radioButton rbFunctional:
     * Change the status selected to Functional
     */
    @FXML
    void rbFunctional_action() {
        setStatus("Functional");
        rbFunctional.setSelected(true);
        rbWreck.setSelected(false);
        rbDestroyed.setSelected(false);
        rbDamaged.setSelected(false);
    }

    /**
     * Action of the radioButton rbWrecked:
     * Change the status selected to Wrecked
     */
    @FXML
    void rbWreck_action() {
        setStatus("Wrecked");
        rbWreck.setSelected(true);
        rbDamaged.setSelected(false);
        rbDestroyed.setSelected(false);
        rbFunctional.setSelected(false);
    }


}
