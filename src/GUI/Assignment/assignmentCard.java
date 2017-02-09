/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Assignment;

import GUI.card.customCard;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Aravind
 */

public class assignmentCard extends customCard {
    
    Assignment card_assignment;
        
    assignmentCard(Assignment asn, AnchorPane overlay){
        
        card_assignment = asn;
        card_overlay_pane = overlay;
        String fullPath = String.format("%s", System.getProperty("user.dir"));
        //System.out.println(fullPath);
        super.init(287, 297, asn.get_title()  + "\n" + asn.get_deadline(), "file:" + fullPath + "/" +   asn.get_icon_path());
        
        super.add_to_class("assignment_card");
        super.add_class_to_label("assignment_card_label");
        super.add_class_to_image("assignment_card_image");
        
//        this.set_on_touch_callback_default();
             
    }
    
    public  void set_on_touch_callback_default( ){
        
        Assignment asn = this.card_assignment;
        
        this.setOnMouseClicked( new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
              
              populate_overlay();
              System.out.println( asn.get_title() + "\n" + asn.get_deadline() );
          }
          }
        );
    }
    
        public void populate_overlay(){
        
        Runnable populate_task = () -> {
            // expensive operations that should not run on the application thread
            
            /* do backed stuff here */
            try {
                Thread.sleep(500);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }


            // update ui -> application thread
            Platform.runLater(() -> {
                System.out.println("Assignment overlay populated");
                card_overlay_pane.setVisible(true);
                card_overlay_pane.setManaged(true);
                
            });
        };
        
        new Thread(populate_task).start();

        
    }  
    
    
}
