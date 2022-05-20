package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainController extends Controller {
    /*
     * A faire:
     * -gestion de la connexion
     * -enlever les if de connexion en commentaire
     * -enlever le bouton de connexion pilot
     * */
    @FXML
    private Button btnConnect;


    @FXML
    private Button btnPilot; //a enelever a la fin

    @FXML
    private Button btnQuit;

    @FXML
    private TextField tfUsername;

    @FXML
    private TextField tfPassword;

    @FXML
    void action_connect(ActionEvent event) /*throws IOException*/ {

        String name = tfUsername.getText();
        System.out.println("\n name = "+name);
        String password = tfPassword.getText();

       if(name.equals("")||password.equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("There are empty fields !");
            errorAlert.showAndWait();
        }

        else if(name.contains(" ")||password.contains(" ")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You can't enter spaces inside username/password !");
            errorAlert.showAndWait();
        }

        //POUR LE LOGIN-----
        /*try {
            Request.logIn(name, password);
        }catch(Exception e){

        }*/

        menuOfficerController menuOfficerController = new menuOfficerController();
        this.open_quit_share(btnConnect, "/vue/menuOfficer.fxml", menuOfficerController, name);

        //ajouter un else if si username est bon mais pas le bon mdp
        //ajouter un else if connection est bonne et entrer dans officer ou pilot selon user

        //super.open_quit(btnConnect,"/vue/menuOfficer.fxml");
    }

    @FXML
    void action_quit(ActionEvent event) {
        super.quit(btnQuit);
    }


    //a enlever à la fin du projet
    @FXML
    void btnPilot_action(ActionEvent event) {
        super.open_quit(btnPilot,"/vue/menuPilot.fxml");
    }

}
