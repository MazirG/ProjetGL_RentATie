package controllers;

import bdd.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import status.TieModel;

/**
 * Controller for the creation of a TieFighter
 * All the fields inside the TieFighter that will be created are displayed on the window
 * The user enters the status and the others are set automatically
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class creationMenuFighterController extends Controller /*implements Initializable*/ {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    private RadioButton rbFighter;

    @FXML
    private RadioButton rbReaper;

    private String modelChosed="";


    /**
     * Action of the Cancel button:
     * Quits the current window and returns to the las menu (Officer menu)
     */
    @FXML
    void btnCancel_action() {

        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

    /**
     * Action oof the Validate button:
     * Gets the model of the TieFIghter choosed by the user in order to create in inside the DB with SQL requests
     * @throws Exception exception to verify SQL requests
     */
    @FXML
    void btnValidate_action() throws Exception {
        if(modelChosed.equals("Fighter") || modelChosed.equals("Reaper") ){
            TieModel tieModel = TieModel.valueOf(this.getModelChosed());
            boolean succeed = true;
            try{
                Request.createTieFighter(tieModel);
            }catch(Exception e){
                succeed=false;
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("The request has failed !");
                errorAlert.showAndWait();
            }
            if(succeed){
                super.open_quit(btnValidate,"/vue/menuOfficer.fxml");
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setContentText("The Tie has been added !");
                errorAlert.show();

            }
        }
        else{
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to choose a model to create a Tie !");
            errorAlert.showAndWait();
        }
        //entrer dans bdd

    }

    /**
     * Action of the radioButton rbFighter:
     * Change le modele selectionné en Fighter
     */
    @FXML
    void rbFighter_action() {
        setModelChosed("Fighter");
        rbFighter.setSelected(true);
        rbReaper.setSelected(false);
    }

    /**
     * Action of the radioButton rbReaper:
     * Change the chosen model to Reaper
     */
    @FXML
    void rbReaper_action() {
        setModelChosed("Reaper");
        rbReaper.setSelected(true);
        rbFighter.setSelected(false);
    }

    /**
     * Setter for the modelChosed
     * @param modelChosed the new model chosen
     */
    public void setModelChosed(String modelChosed) {
        this.modelChosed = modelChosed;
    }

    /**
     * Getter for the modelChosed
     * @return the modelChosed
     */
    public String getModelChosed() {
        return modelChosed;
    }


}
