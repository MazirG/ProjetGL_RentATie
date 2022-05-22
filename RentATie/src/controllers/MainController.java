package controllers;

import bdd.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import status.UserPost;

/**
 * Controller for the 1st window of the application, the connexion window (main Menu)
 * The user simply has to enter his IDS and he will be connected has a Pilot or as an Officer
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class MainController extends Controller {

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnQuit;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField tfPassword;

    /**
     * Action du bouton connect
     * Prend en compte les différents champs entrés et lance une connexion
     * Action of the Connect button:
     * Takes the data entered inside the fields and connects the user if connexion succeeds
     * Takes in account the cases where fields are empty/contains spaces, case where the logs ID are incorrect
     * @throws Exception exceptions when SQL requests fail
     */

    @FXML
    void action_connect() throws Exception {
        /*tfPassword.setText("123");
        tfUsername.setText("Pseudo1");*/
        String name = tfUsername.getText();
        String password = tfPassword.getText();
        UserPost typeUser = Request.post(name);

       if(name.equals("")||password.equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("There are empty fields !");
            errorAlert.showAndWait();
        }

       else if(name.contains(" ")||password.contains(" ")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You can't enter spaces inside the fields !");
            errorAlert.showAndWait();
        }

       if(typeUser!=null) {
             int logged = Request.logIn(name,password);
             if (logged==-1){
                 Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                 errorAlert.setHeaderText("Error");
                 errorAlert.setContentText("Connexion error: incorrect logs !");
                 errorAlert.showAndWait();
             }
             else if(typeUser.equals(UserPost.Officer)){
                 menuOfficerController menuOfficerController = new menuOfficerController();
                 this.open_quit_share(btnConnect, "/vue/menuOfficer.fxml", menuOfficerController, logged);
             }

             else if(typeUser.equals(UserPost.Pilot)){
                 menuPilotController menuOfficerController = new menuPilotController();
                 this.open_quit_share(btnConnect, "/vue/menuPilot.fxml", menuOfficerController, logged);
             }

             }
       else{
           Alert errorAlert = new Alert(Alert.AlertType.ERROR);
           errorAlert.setHeaderText("Error");
           errorAlert.setContentText("Connexion error: incorrect logs !");
           errorAlert.showAndWait();
       }

    }

    /**
     * Action of the Quit button:
     * Closes the application
     */
    @FXML
    void action_quit() {
        super.quit(btnQuit);
    }


}
