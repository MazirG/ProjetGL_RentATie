package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

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

    @FXML
    void btnCancel_action() {
        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");
        //ajouter remise a zero des champs pr datas de la bd
    }

    @FXML
    void btnValidate_action() {


        /* Creer un nouveau flight
        * assignFlight() depuis un officer
        *
        * */
        //entrer dans bdd
        LocalDate dateEnd = tfEndDate.getValue();
        LocalDate dateBegin = tfBeginDate.getValue();
        if(dateEnd == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to enter a rent back date to modify the data !");
            errorAlert.showAndWait();
        }


        else if(dateBegin == null){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to enter a rent back date to modify the data !");
            errorAlert.showAndWait();
        }

        else if(tfName.getText().isEmpty() || tfName.getText().contains(" ")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("The mission name entered is incorrect !");
            errorAlert.showAndWait();
        }
         /*date.getYear();
        date.getMonth();
        date.getDayOfMonth();*/
        super.open_quit(btnValidate,"/vue/menuOfficer.fxml");

    }
}
