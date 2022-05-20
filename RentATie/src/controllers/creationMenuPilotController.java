package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class creationMenuPilotController extends Controller {
    /*
     * A faire:
     * -tout (juste copier menu modify)
     *
     *  */
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    private TextField tfAge;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    void btnCancel_action() {
        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");
        //annuler les champs etc

    }

    @FXML
    void btnValidate_action() {
        //entrer dans bdd


        //CREER UN PILOT ------------
        /*Officer off = new Officer("xx","xx",12);
        Pilot pilot= new Pilot()
        off.createUser();*/
        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

}
