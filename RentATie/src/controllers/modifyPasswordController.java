package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;


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

    @FXML
    void btnCancel_action(ActionEvent event) {
        //if dans menuOfficer
        super.quit(btnCancel);
        //if dans menuPilot
        //super.open_quit(btnCancel,"/vue/menuPilot.fxml");
    }

    @FXML
    void btnValidate_action(ActionEvent event) {

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

        else if(s2.equals(s3) /*&& s1=ancien password*/){
            //changer dans le conf des mdp/user
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            //errorAlert.setHeaderText("New");
            errorAlert.setContentText("Your password has been changed !");
            errorAlert.show();
            quit(btnValidate);
        }
        /*else if(s1!= ancien password){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            //errorAlert.setHeaderText("Old password not valid");
            errorAlert.setContentText("The old password entered is incorect !");
            errorAlert.showAndWait();
        }*/
        else if(!s2.equals(s3)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to enter the same password twice !");
            errorAlert.showAndWait();
        }
        System.out.println("\n"+s1+" "+s2+" "+s3+"\n");

        //if dans menuOfficer
        //super.open_quit(btnValidate,"/vue/menuOfficer.fxml");
        //if dans menuPilot
        //super.open_quit(btnCancel,"/vue/menuPilot.fxml");

    }

}
