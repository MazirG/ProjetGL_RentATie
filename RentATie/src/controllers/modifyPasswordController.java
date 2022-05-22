package controllers;

import bdd.Request;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

/**
 * Controller for the modification of a password's user
 * This window is accessible by both the Pilot and Officer
 * The user just have to enter the fields correctly and his own password will be modified correctly in the DB
 * (each user can only modify his own password, an officer can't modify pilot's password)
 * Extends the Controller class to get the data required for the action and to communicate with other windows
 */
public class modifyPasswordController extends Controller {
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnValidate;

    @FXML
    private PasswordField tfNewPassword1;

    @FXML
    private PasswordField tfNewPassword2;

    @FXML
    private PasswordField tfOldPassword;

    /**
     * Action of the Cancel button:
     * Closes the window and cancels the modification
     */
    @FXML
    void btnCancel_action() {
        super.quit(btnCancel);
    }

    /**
     * Action of the Validate button:
     * Gets all the data entered by the user and updates the user password of the current user connected
     * Takes in account the case where the fields are empty/contains spaces, case where passwords entered don't corresponds to the DB or to other fields
     * Cases where SQL requests fail
     * @throws Exception excetions for the SQL requests
     */
    @FXML
    void btnValidate_action() throws Exception {

        String s1=tfOldPassword.getText();
        String s2=tfNewPassword1.getText();
        String s3=tfNewPassword2.getText();



        if(s1.equals("")||s2.equals("")||s3.equals("")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("The fields cannot be empty !");
            errorAlert.showAndWait();
        }

        else if(s1.contains(" ")||s2.contains(" ")||s3.contains(" ")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("The fields cannot contain space caracters !");
            errorAlert.showAndWait();
        }

        else if(!s2.equals(s3)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to enter the same password twice !");
            errorAlert.showAndWait();
        }

        else if(s2.equals(s3)){
            String username =Request.username(this.getUserId());
            int test =Request.logIn(username,s1);
            if(test==this.getUserId()){
                boolean succeed = true;
                try{
                    Request.modifyUserPassword(test,s2);
                }catch (Exception e){
                    succeed=false;
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setContentText("The modification has failed !");
                    errorAlert.showAndWait();
                }
                if(succeed) {
                    Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                    errorAlert.setContentText("Your password has been changed !");
                    errorAlert.show();
                    quit(btnValidate);
                    }
            }
            else{
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setContentText("The old password entered is incorect !");
                errorAlert.showAndWait();
            }
        }



    }

}
