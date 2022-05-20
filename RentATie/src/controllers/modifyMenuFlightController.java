package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class modifyMenuFlightController extends Controller {
    /*
     * A faire:
     * lecture objet + ecrire bdd
     * */
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    private DatePicker tfDate;

    @FXML
    void btnCancel_action(ActionEvent event) {
        open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

    @FXML
    void btnValidate_action(ActionEvent event) {
        //verifs data

        /* MODIFIER UN FLIGHT
        *Officer off = new Officer("xx","xx",12);
        *
        * flightDone()
        * */
        LocalDate date = tfDate.getValue();
        if(date == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to enter a rent back date to modify the data !");
            errorAlert.showAndWait();
        }
        else{

        }
        /*date.getYear();
        date.getMonth();
        date.getDayOfMonth();*/
        //System.out.println(date.toString());
    }
}
