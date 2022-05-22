package controllers;

import bdd.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import status.UserPost;

/**
 * Controller for the creation of a Pilot
 * All the fields inside the Pilot that will be created are displayed on the window
 * The user enters his ID as user(username,password),his day, his age,and the others are set automatically
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class creationMenuPilotController extends Controller /*implements */ {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    private TextField tfAge;

    @FXML
    private TextField tfName;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private TextField tfUsername;

    /**
     * Action for the Cancel button:
     * Cancels the creation, closes this window, and opens the last window (officer Menu)
     */
    @FXML
    void btnCancel_action() {
        super.open_quit(btnCancel,"/vue/menuOfficer.fxml");


    }

    /**
     * Get the date given by the user, verifies and creates the Pilot in the DB with an SQL request
     * Takes in account the case where the fields are empty/ contains spaces, case where name contains numbers, case where age is negative/contains letters
     * Cases where SQL requests failed
     * @throws Exception exceptions for the SQL request
     */
    @FXML
    void btnValidate_action() throws Exception {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String name = tfName.getText();
        String age = tfAge.getText();



        if(username.equals("")||password.equals("")||name.equals("")||age.equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to enter all the info !");
            errorAlert.showAndWait();
        }
        else if(username.contains(" ") || password.contains(" ") || name.equals(" ") || age.equals(" ")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("The fields cannot contain spaces !");
            errorAlert.showAndWait();
        }

        else if(!name.matches("[a-zA-Z]+")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("The name contains only letters !");
            errorAlert.showAndWait();
        }

        else {
            int ageInt = -1;
            boolean estInt = true;
            try {
                ageInt = Integer.parseInt(age);
            } catch (NumberFormatException e) {
                estInt = false;
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("You need to enter a number for the age !");
                errorAlert.showAndWait();
            }

            if(estInt && ageInt>0){
                boolean succeed=Request.createUser(UserPost.Pilot,name,username,Integer.parseInt(age),password);
                if(!succeed){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Error");
                    errorAlert.setContentText("Username already used !");
                    errorAlert.showAndWait();
                }
                else{
                    super.open_quit(btnValidate,"/vue/menuOfficer.fxml");
                    Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                    errorAlert.setContentText("The Pilot has been added !");
                    errorAlert.show();
                }

            }
            else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("You need to enter a positive number !");
                errorAlert.showAndWait();
            }
        }
    }


}
