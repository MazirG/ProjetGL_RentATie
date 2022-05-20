package controllers;

import fly.Flight;
import fly.TieFighter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import login.Pilot;


public abstract class Controller {


    /*
     * A faire:
     * - rien pour le moment
     * */
    private Pilot pilot;
    private TieFighter tieFighter;
    private Flight flight;

    public String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Flight getFlight() {
        return flight;
    }

    public Pilot getPilot() {
        return pilot;
    }



    public TieFighter getTieFighter() {
        return tieFighter;
    }

    public void setNull(){
        pilot=null;
        tieFighter=null;
        flight=null;
        userName=null;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public void setTieFighter(TieFighter tieFighter) {
        this.tieFighter = tieFighter;
    }



    public void open(String pageName){
        Parent fxml;
        Stage home = new Stage();
        try{
            //System.out.println("test");
            fxml = FXMLLoader.load(getClass().getResource(pageName));
            Scene scene = new Scene(fxml);
            home.setScene(scene);
            home.show();
        }
        catch (Exception e){
            System.out.println(" \n\n\n test \n\n\n");
            e.printStackTrace();
        }
    }

    public void quit(Button bout){
        Stage stage = (Stage) bout.getScene().getWindow();
        stage.close();
    }

    public void open_quit(Button bout, String pageName){
        open(pageName);
        quit(bout);
    }


    public void open_share2(){

    }
    public void open_share(String pageName, Controller controller, Object obj){
        try{
            //System.out.println("test");
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pageName));
            Parent root = loader.load();
            controller=loader.getController();
            //controller.setNull();
            if(obj instanceof Pilot){
                controller.setPilot((Pilot) obj);
            }

            else if(obj instanceof TieFighter && pageName.equals("/vue/creationMenuFlight.fxml")){
                controller.setTieFighter((TieFighter) obj);
                controller.setPilot(this.getPilot());
            }

            else if(obj instanceof TieFighter){
                controller.setTieFighter((TieFighter) obj);
            }

            else if(obj instanceof Flight){
                controller.setFlight((Flight) obj);
            }

            else if(obj instanceof String){
                System.out.println("\nc bon");
                controller.setUserName((String) obj);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        }
        catch (Exception e){
            System.out.println(" \n\n\n test \n\n\n");
            e.printStackTrace();
        }
    }

    public void open_quit_share(Button bout,String pageName,Controller controller, Object obj){
        open_share(pageName,controller,obj);
        quit(bout);
    }
}
