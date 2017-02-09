/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.card;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Aravind
 */

public class customCard extends StackPane{
    
    /* Icon for card and a Label beneath it */
    
    /* Label diplayed on card */
    protected Label card_label;
    protected Image card_image;
    /* Main Image on card */
    protected ImageView card_image_veiw;
    
    /* Pane to be overlayed over veiw when acrd is clicked */
    protected AnchorPane card_overlay_pane;

    
    public customCard(){}
    
    public void init(int card_length, int card_breadth, String label, String icon_path) {
        
        super.setPrefSize(card_length, card_breadth);
        super.getStyleClass().addAll("custom_card");
        
        
        card_label = new Label( label );
        card_image = new Image( icon_path );
        
        card_label.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
        
        card_image_veiw = new ImageView();
        card_image_veiw.setImage( card_image );
        
        set_image_dim(90, 90);
        show_image(true);
        
        card_image_veiw.setPreserveRatio(true);
        card_image_veiw.setSmooth(true); // what does this actually do?
        
        /* Add custom classes for CSS */
        card_image_veiw.getStyleClass().addAll("custom_card_image");
        card_label.getStyleClass().addAll("custom_card_label");

        /* Set alignment */
        StackPane.setAlignment(card_image_veiw, Pos.TOP_CENTER);
        StackPane.setAlignment(card_label, Pos.BOTTOM_CENTER);

        /* Adding to card */
        super.getChildren().addAll(card_image_veiw);
        super.getChildren().addAll(card_label);

        
        
        
    }
    
    /* utility functions */
    public void add_to_class(String class_name){
      this.getStyleClass().addAll( class_name );
    }
    
    public void add_class_to_label(String class_name){
        card_label.getStyleClass().addAll( class_name );
    }
    
    public void add_class_to_image(String class_name){
        card_image_veiw.getStyleClass().addAll( class_name );
    }
    
    public void show_image(boolean state){
        card_image_veiw.setVisible(state);
    }
    
    public void set_image_dim(double height, double width){
        card_image_veiw.setFitHeight(height);
        card_image_veiw.setFitWidth(width);
    }
    
    public String get_label_text(){
        return card_label.getText();
    }
    
    public void set_image_alignment(Pos position){
        StackPane.setAlignment(card_image_veiw,position);
    }
    
    public void set_label_alignment(Pos pos){
         StackPane.setAlignment(card_label, pos);
    }
       
}
