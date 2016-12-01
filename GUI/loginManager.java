/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.controllers.LoginPageController;
import java.io.IOException;
import java.util.logging.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;

/**
 *
 * @author Aravind
 */
public class loginManager {    
    private Scene scene;
    
    public loginManager(Scene scene) {
        this.scene = scene;
    }
    
    public void authenticated(){
        
        show_main_screen();

    }
    
    public void logout(){ 
        
        show_login_screen(false);
    }
    
    public void show_login_screen(boolean error){
        try{
        
            FXMLLoader loader = new FXMLLoader( getClass().getResource("veiws/loginPage.fxml") );
            scene.setRoot((Parent) loader.load());
            

            LoginPageController controller = loader.<LoginPageController>getController();
            controller.setupPage(this, error);
                 
            
        } catch (IOException ex) {
            Logger.getLogger(loginManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void show_main_screen(){
        
          applicationManager appManager = new applicationManager(this.scene);
          appManager.init_screen();


    }
}

