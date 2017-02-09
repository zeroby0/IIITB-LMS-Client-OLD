/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Course;

import GUI.card.customCard;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Aravind
 */

public class courseCard extends customCard {
    
    Course card_course;
        
    courseCard(Course course, AnchorPane overlay){
        
        
        card_course = course;
        card_overlay_pane = overlay;
        String fullPath = String.format("%s", System.getProperty("user.dir"));
        //System.out.println(fullPath);
        super.init(287, 297, course.get_title() +"\n" + course.get_instructor(), "file:" + fullPath + "/" +   course.get_icon_path());
        
        super.add_to_class("course_card");
        super.add_class_to_label("course_card_label");
        super.add_class_to_image("course_card_image");
        
//        this.set_on_touch_callback_default();        
    }
    
    public  void set_on_touch_callback_default( ){
        
        Course cr = this.card_course;
        customCard crd = this;
        
        this.setOnMouseClicked( new EventHandler<MouseEvent>() {
          @Override public void handle(MouseEvent event) {
              
              populate_overlay();
              System.out.println(cr.get_title() + "\n" + cr.get_instructor()  );
          }
          }
        );
    }
    
    public void populate_overlay(){
        courseOverlay.populate_overlay(card_course);
        courseOverlay.get_label().setText( card_course.get_title() );
        card_overlay_pane.setVisible(true);
        card_overlay_pane.setManaged(true);

        
//        Runnable populate_task = () -> {
//            // expensive operations that should not run on the application thread
//            
//            /* do backed stuff here */
//
//
//            courseOverlay.populate_overlay(card_course);
//
//
//            // update ui -> application thread
//            Platform.runLater(() -> {
//                System.out.println("Course overlay populated");
//                card_overlay_pane.setVisible(true);
//                card_overlay_pane.setManaged(true);
//                
//            });
//        };
//        
//        new Thread(populate_task).start();

        
    } 

}   
