/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import GUI.controllers.MainPageController;
import java.io.IOException;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Aravind
 */
public class applicationManager {
    private Scene scene;
    
    public applicationManager(Scene scene){
        this.scene = scene;
    }

    
    public void init_screen(){
        
        try{
        
            FXMLLoader loader = new FXMLLoader( getClass().getResource("veiws/mainPage.fxml") );
            scene.setRoot((Parent) loader.load());

            MainPageController controller = loader.<MainPageController>getController();
            controller.setup_page(this);   
            
        } catch (IOException ex) {
            Logger.getLogger(loginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
