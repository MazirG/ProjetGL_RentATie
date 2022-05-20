package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class modifyMenuPilotController extends Controller {
    /*
     * A faire:
     *-lecture objet + ecriture bdd
     * */
    @FXML
    private Button btnValidate;

    @FXML
    private Button btnCancel;

    @FXML
    private RadioButton rbDead;

    @FXML
    private RadioButton rbLost;

    @FXML
    private RadioButton rbNo;

    @FXML
    private RadioButton rbSafe;

    @FXML
    private RadioButton rbWounded;

    @FXML
    private RadioButton rbYes;

    @FXML
    private TextField tfShip;

    private int inFlight = -1;
    private String status="";

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInFlight(int inFlight) {
        this.inFlight = inFlight;
    }

    @FXML
    void btnCancel_action(ActionEvent event) {
        open_quit(btnCancel,"/vue/menuOfficer.fxml");
    }

    @FXML
    void btnValidate_action(ActionEvent event) {
        /*
        *
        * */
        boolean estNbre = true;
        if( inFlight==-1 || ( status.equals("WOUNDED")==false && status.equals("DEAD")==false && status.equals("LOST")==false && status.equals("SAFE")==false )){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("You need to chose flight and physical status to modify !");
            errorAlert.showAndWait();
        }

        else{
            int nbShip;
            try{
                nbShip=Integer.parseInt(tfShip.getText());
            }catch (NumberFormatException e){
                estNbre=false;
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("You need to enter a number to modify the data !");
                errorAlert.showAndWait();
            }

        }
        if(estNbre) {
            //if 1 = pilote inFlight, si 0 pilote pas inFlight
            System.out.println("c bon");
        }
        //open("/vue/modifyPassword.fxml");

    }


    @FXML
    void rbYes_action(ActionEvent event) {
        setInFlight(1);
        rbNo.setSelected(false);
        rbYes.setSelected(true);
    }

    @FXML
    void rbNo_action(ActionEvent event) {
        setInFlight(0);
        rbYes.setSelected(false);
        rbNo.setSelected(true);
    }

    @FXML
    void rbLost_action(ActionEvent event) {
        setStatus("LOST");
        rbWounded.setSelected(false);
        rbDead.setSelected(false);
        rbSafe.setSelected(false);
        rbLost.setSelected(true);

    }

    @FXML
    void rbWounded_action(ActionEvent event) {
        setStatus("WOUNDED");
        rbLost.setSelected(false);
        rbDead.setSelected(false);
        rbSafe.setSelected(false);
        rbWounded.setSelected(true);
    }

    @FXML
    void rbSafe_action(ActionEvent event) {
        setStatus("SAFE");
        rbWounded.setSelected(false);
        rbDead.setSelected(false);
        rbLost.setSelected(false);
        rbSafe.setSelected(true);
    }

    @FXML
    void rbDead_action(ActionEvent event) {
        setStatus("DEAD");
        rbLost.setSelected(false);
        rbWounded.setSelected(false);
        rbSafe.setSelected(false);
        rbDead.setSelected(true);

    }

}
