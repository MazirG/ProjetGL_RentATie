package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class creationMenuFighterController extends Controller {
    /*
     * A faire:
     *-tout (juste copier modify a peu pres)
     * */
    private Parent fxml;
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    void btnCancel_action() {

        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");

    }

    @FXML
    void btnValidate_action() {
        //entrer dans bdd
        super.open_quit(btnValidate,"/vue/menuOfficer.fxml");
    }

}
