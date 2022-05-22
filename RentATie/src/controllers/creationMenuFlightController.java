package controllers;

import bdd.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
/**
 * Controller for the creation of a Flight
 * All the fields inside the TieFighter that will be created are displayed on the window
 * The user enters the name of the mission, the begin rent and ending rent day, the others are set automatically
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class creationMenuFlightController extends Controller {
    /*
     * A faire:
     *-Les trucs avec les dates (comparaisons/ecritures de date)
     * */
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    private DatePicker tfBeginDate;

    @FXML
    private DatePicker tfEndDate;

    @FXML
    private TextField tfName;

    /**
     * Action of the Cancel button:
     * Cancels the assignment, closes the window and returns to the last menu (menu Officer)
     */
    @FXML
    void btnCancel_action() {
        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

    /**
     * Action of the Validate button:
     * Creates a Flight by getting the data entered by the usr and stocks the result inside the DB
     * Takes in account the case where the final date is befor the begin date, the cas where fields are empty, the case where SQL request fails
     */
    @FXML
    void btnValidate_action() {

        LocalDate dateEnd = tfEndDate.getValue();
        LocalDate dateBegin = tfBeginDate.getValue();
        if(dateEnd == null || dateBegin == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to enter rent dates !");
            errorAlert.showAndWait();
        }


        else if(tfName.getText().isEmpty() || tfName.getText().contains(" ")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("The mission name entered is incorrect !");
            errorAlert.showAndWait();
        }

        else if (dateBegin.compareTo(dateEnd)>=0){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("The begin date must be before the end date !");
            errorAlert.showAndWait();
        }

        else{
            boolean succeed = true;
            try{
                System.out.println("\nDATE DE FIN FIN\n"+tfEndDate.getValue().toString());
                Request.assignFlight(this.getPilot().getId(),this.getTieFighter().getFighterId(),tfName.getText(),tfBeginDate.getValue().toString(),tfEndDate.getValue().toString());
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
                errorAlert.setContentText("The Flight has been created !");
                errorAlert.show();
            }


        }


    }
}
