package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/vue/mainMenu.fxml"));
        //primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        launch(args);

        //Officer off1 = new Officer(1,"Pseudo1","Azerty123");
        //Pilot pil1 = new Pilot("Pseudo2", "Azerty321", 21,"Nom2", 20);

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
