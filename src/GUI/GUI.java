/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import Backend.Backend;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Aravind
 */
public class GUI extends Application {
    

    @Override
    public void start(Stage stage) throws Exception {
        
        Scene scene = new Scene(new StackPane());
        
        loginManager new_login_manager = new loginManager(scene);
        
        new_login_manager.show_login_screen(false);
        
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        
    }
    

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Runnable gui_start = () -> {
            launch(args);
            Platform.runLater(() -> {
                System.out.println("GUI Started");        
            });
        
        };
                
        new Thread(gui_start).start();
        Runnable backend_start = () -> {
            
            Backend.table_setup();
            Platform.runLater(() -> {
                System.out.println("Backend tables setup");          
            });
        
        };
                
        new Thread(backend_start).start();
        
                
                
        
    }
}
