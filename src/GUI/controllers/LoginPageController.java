/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.controllers;

import Backend.Backend;
import GUI.loginManager;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Aravind
 */
public class LoginPageController implements Initializable {
    
    @FXML private Button loginButton;
    @FXML private TextField userName_box;
    @FXML private PasswordField password_box;
    @FXML private CheckBox rememberMe_chk;
    @FXML private Label tempLogin_label;
    @FXML private Label info_label;
    @FXML private StackPane preloader_pane;
    @FXML private Pane login_pane;
    @FXML Parent root;
    
    loginManager manager;
    

    @FXML
    public void keyPressed(KeyEvent event){
        switch (event.getCode()) {

            case ENTER:
                
                if ( !login() ) {
                    info_label.setVisible(true);
                  }else{
                      info_label.setVisible(false);
                      //t_login_manager.authenticated();
                }
                break;

            default:
                break;
        }
    }
    public void setupPage(final loginManager t_login_manager, boolean error) {
        
        manager = t_login_manager;
        info_label.setVisible(error);

        
        loginButton.setOnAction( new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent event) {


                  if ( !login() ) {
                    info_label.setVisible(true);
                  }else{
                      info_label.setVisible(false);
                      //t_login_manager.authenticated();
                  }
                  


          }
          }
        );
        
        tempLogin_label.setOnMouseClicked( new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {

              
               if ( !temp_login() ) {
                      info_label.setVisible(true);
               }else{
                      info_label.setVisible(false);
                      //t_login_manager.authenticated();                  
              }

          }
          }
        );
        
        String[] creds = new String[2];
        if(Backend.usercredentials(creds)){
            userName_box.setText(creds[0]);
            password_box.setText(creds[1]);
            
        }
        
        
    }
    
    @FXML
    public boolean login(){
        //TODO backend check if user credentaials valid
//        System.out.println(userName_box.getText());
//        System.out.println(password_box.getText());
//        System.out.println(rememberMe_chk.isSelected());
        
//        helper.setup_preloader(preloader_pane, "7.gif");
        
        String fullPath = String.format("%s", System.getProperty("user.dir"));
        
        
        Image image = new Image( "file:" + fullPath + "/" +   "src/GUI/resources/main/preloaders/6.gif" );
        ImageView splashGif = new ImageView(image);
        splashGif.setCache(true);
        splashGif.setSmooth(true);
        
        
        
//        preloader_pane.setOpacity();
        preloader_pane.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(300), preloader_pane);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        
//        splashGif.setFitWidth(preloader_pane.getWidth() / 2 );
//        splashGif.setFitHeight(preloader_pane.getHeight() / 2);
        splashGif.setPreserveRatio(true);
        preloader_pane.getChildren().add(splashGif);
        login_pane.setVisible(false);
//        


        
        
        Runnable login_task = () -> {
            // expensive operations that should not run on the application thread
            
            /* Login */
            
            boolean is_logged_in = Backend.api.login( userName_box.getText(),password_box.getText() );
            System.out.print("Login: ");
            System.out.println(is_logged_in);
            


            // update ui -> application thread
            Platform.runLater(() -> {

//                        ft.setFromValue(1.0);
//                        ft.setToValue(0.0);
//                        ft.play();
                info_label.setVisible(false);
                if(is_logged_in){
                    
                    boolean user_exist = Backend.user_exists(userName_box.getText());
                    System.out.print("User: ");
                    System.out.println(user_exist);
                    
                    if(user_exist){
                        this.manager.authenticated();
                    }else{
                        try {
                            System.out.println("Setting up");
                            
                            Runnable setup_task = () -> {
                                try {
                                    Backend.setup(userName_box.getText(), password_box.getText());
                                } catch (Exception ex) {
                                    
                                }
                                Platform.runLater(() -> {
                                    this.manager.authenticated();
                                
                                });
                                
                            };
//                            Backend.setup(userName_box.getText(), password_box.getText());
//                            System.out.println("Setup complete");
//                            this.manager.authenticated();
//                            System.out.println("Auth");
                            
                            new Thread(setup_task).start();
                            
                            
                        } catch (Exception ex) {
                            
                        }
                    }
                    
                }else{
                        info_label.setVisible(true);
                        this.manager.show_login_screen(true);
                }
                
            });
        };
        
        new Thread(login_task).start();
        return false;
    }
    
    @FXML
    public boolean temp_login(){
//
//        System.out.println(userName_box.getText());
//        System.out.println(password_box.getText());
//        
        
        return false;
        
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        preloader_pane.setVisible(false);
        preloader_pane.setOpacity(0);
        login_pane.setVisible(true);
        
    }    
    
}


