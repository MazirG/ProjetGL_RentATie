package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class modifyMenuFighterController extends Controller {
    /*
     * A faire:
     * - lecture objet recu + ecriture bdd
     * */
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    private Label labelSelected;


    @FXML
    private RadioButton rbDamaged;

    @FXML
    private RadioButton rbDestroyed;

    @FXML
    private RadioButton rbFunctional;

    @FXML
    private RadioButton rbWreck;

    private String status="";


    public void setStatus(String status) {
        this.status = status;
    }

    @FXML
    void btnCancel_action(ActionEvent event) {
        open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

    @FXML
    void btnValidate_action(ActionEvent event) {
        /*
        * modify
        * create officer (osef des trucs)
        * modifyFighter
        * */
        if(status.equals("WRECK")==false && status.equals("FUNCTIONAL")==false && status.equals("DESTROYED")==false && status.equals("DAMAGED")==false){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to chose a status to modify !");
            errorAlert.showAndWait();
        }
        else{
            System.out.println("c bon");
        }
    }

    @FXML
    void rbDamaged_action(ActionEvent event) {
        setStatus("DAMAGED");
        rbWreck.setSelected(false);
        rbDestroyed.setSelected(false);
        rbFunctional.setSelected(false);
    }

    @FXML
    void rbDestroyed_action(ActionEvent event) {
        setStatus("DESTROYED");
        rbWreck.setSelected(false);
        rbDamaged.setSelected(false);
        rbFunctional.setSelected(false);
    }

    @FXML
    void rbFunctional_action(ActionEvent event) {
        setStatus("FUNCTIONAL");
        rbWreck.setSelected(false);
        rbDestroyed.setSelected(false);
        rbDamaged.setSelected(false);
    }

    @FXML
    void rbWreck_action(ActionEvent event) {
        setStatus("WRECK");
        rbDamaged.setSelected(false);
        rbDestroyed.setSelected(false);
        rbFunctional.setSelected(false);
    }

}
