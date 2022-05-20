package test;

import bdd.Request;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.Officer;
import login.Pilot;
import status.FighterStatus;
import status.PilotStatus;
import status.UserPost ;

import static status.TieModel.Hunter;
import static status.TieModel.XWing;

public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/vue/mainMenu.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);

        Officer off1 = new Officer(1,"Pseudo1","Azerty123");
        Pilot pil1 = new Pilot("Pseudo2", "Azerty321", 21,"Nom2", 20);

        //Request.logIn("Pseudo1", "Azerty321");

    }
}

/*public class Main {

    public static void main(String[] args) throws Exception {


        Officer off1 = new Officer(1,"Pseudo1","Azerty123");
        Pilot pil1 = new Pilot("Pseudo2", "Azerty321", 21,"Nom2", 20);

        Request.displayPilotAvailable();
        Request.displayTieFighterAvailable();
    }
}*/
