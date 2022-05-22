package controllers;

import fly.Flight;
import fly.TieFighter;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import login.Pilot;

/**
 * An abstract class that contains all the methods and fields that concerns the communication between the windows
 * It has methods about changing pages, and fields Pilot,TieFighter,FLight,UserId,listLate that are changing when
 * some information need to go from a window to another
 */
public abstract class Controller {

    private Pilot pilot;
    private TieFighter tieFighter;
    private Flight flight;

    public int UserId;
    public ObservableList listLate;

    /**
     * Setter for the userID
     * @param id the new UserID
     */
    public void setUserId(int id) {
        UserId = id;
    }

    /**
     * Getter of userID
     * @return the userID
     */
    public int getUserId() {
        return UserId;
    }


    /**
     * Getter for the flight
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Getter for the pilot
     * @return the pilot
     */
    public Pilot getPilot() {
        return pilot;
    }


    /**
     * Getter for the tieFighter
     * @return the tieFighter
     */
    public TieFighter getTieFighter() {
        return tieFighter;
    }


    /**
     * Getter for the listLate
     * @return the listLate
     */
    public ObservableList getListLate() {
        return listLate;
    }

    /**
     * Setter for the flight
     * @param flight the new flight
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Setter for the pilot
     * @param pilot the new pilot
     */
    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    /**
     * Setter for the tieFighter
     * @param tieFighter the new tieFighter
     */
    public void setTieFighter(TieFighter tieFighter) {
        this.tieFighter = tieFighter;
    }

    /**
     * Setter for the listLate
     * @param listLate the new listLate
     */
    public void setListLate(ObservableList listLate) {
        this.listLate = listLate;
    }

    /**
     * Method that allows to open a new window with its pageName
     * takes in account the case where page don't open
     * @param pageName the name of the FXML file to open
     */
    public void open(String pageName){
        Parent fxml;
        Stage home = new Stage();
        try{
            fxml = FXMLLoader.load(getClass().getResource(pageName));
            Scene scene = new Scene(fxml);
            home.setScene(scene);
            home.setResizable(false);
            home.sizeToScene();
            home.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Method that allows to quit a window with the button that triggered the action
     * @param bout the button that allows to quit the window
     */
    public void quit(Button bout){
        Stage stage = (Stage) bout.getScene().getWindow();
        stage.close();
    }

    /**
     * Method that allows to open a new window pageName and close the actual window that contains the button clicked
     * @param bout the button
     * @param pageName name of the FXML file printed in the new window
     */
    public void open_quit(Button bout, String pageName){
        open(pageName);
        quit(bout);
    }

    /**
     * Method that allows to open a new window pageName and to transfer the Object obj with its controller
     * checks the type of the object and do the action that corresponds to the type of the obj
     * @param pageName name of the FXML file that will be displayed in the new table
     * @param controller controller that will give the object to the next window
     * @param obj object that will be transfered in the nex page
     */
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

            else if(obj instanceof ObservableList){
                controller.setListLate((ObservableList) obj);
            }

            else if(obj instanceof Flight){
                controller.setFlight((Flight) obj);
            }

            /*else if(obj instanceof String){
                //System.out.println("\nc bon");
                controller.setUserName((String) obj);
            }*/

            else if(obj instanceof Integer){
                controller.setUserId((Integer) obj);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.sizeToScene();
            stage.show();

        }
        catch (Exception e){
            //System.out.println(" \n\n\n test \n\n\n");
            e.printStackTrace();
        }
    }

    /**
     * Methode permettant de quitter la fenetre actuelle contenant le bouton bout, d'ouvrir une nouvelle fenetre pageName en lui transmettant un objet obj a l'aide de son controller
     * Method that allows to quit the current window with the button bout, open a new window pageName and transfer the Object obj to it with its controller
     * @param bout the button that triggered the action
     * @param pageName name of the FXML file that will be printed in the new window
     * @param controller controller that will transfer the object to the next window
     * @param obj object that will be transfered to the next window
     */
    public void open_quit_share(Button bout,String pageName,Controller controller, Object obj){
        open_share(pageName,controller,obj);
        quit(bout);
    }
}
