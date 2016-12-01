/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Note;

import GUI.card.customCard;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Aravind
 */
public class noteCard extends customCard{
    noteCard(Note note, AnchorPane overlay){
        
        card_overlay_pane = overlay;
        String description = note.get_title() + ":   " + note.get_description(); 
        
        String fullPath = String.format("%s", System.getProperty("user.dir"));
        super.init(1000, 95, description, "file:" + fullPath + "/" +   note.get_icon_path() );
        
                
        super.add_to_class("note_card");
        super.add_class_to_label("note_card_label");
        super.add_class_to_image("note_card_image");
        super.set_image_dim(60,60);
        super.set_image_alignment(Pos.CENTER_LEFT);
        super.set_label_alignment(Pos.CENTER_RIGHT);
    }
    
    public  void set_on_touch_callback_default( ){
        
        customCard cr = this;
        
        this.setOnMouseClicked( new EventHandler<MouseEvent>() {
            
          @Override public void handle(MouseEvent event) {


              System.out.println(cr.get_label_text()  );
          }
          }
        );
    }
    
}
