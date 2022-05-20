package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class consultMenuPilotController extends Controller {
    @FXML
    private Button btnModify;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnDelete;

    @FXML
    void btnCancel_action(ActionEvent event) {
        quit(btnCancel);
    }

    @FXML
    void btnDelete_action(ActionEvent event) {
        Alert errorAlert = new Alert(Alert.AlertType.CONFIRMATION);
        errorAlert.setHeaderText("Effacer ?");
        errorAlert.setContentText("Etes vous sur d'effacer la donnée !");
        errorAlert.showAndWait();
    }

    @FXML
    void btnModify_action(ActionEvent event) {
        open("/vue/modifyMenuPilot.fxml");
    }
}
