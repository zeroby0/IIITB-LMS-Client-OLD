/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

import GUI.card.customCard;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Aravind
 */
public class forumCard extends customCard {
    Forum card_forum;
    
    public forumCard(Forum forum){
        this.card_forum = forum;
        ArrayList<Forum> list_of_forums = new ArrayList<Forum>(); 
        String fullPath = String.format("%s", System.getProperty("user.dir"));
        super.init(1000, 550, forum.get_title(), "file:" + fullPath + "/" +   forum.get_icon_path() );
        
        super.add_to_class("forum_card");
        super.add_class_to_label("forum_card_label");
        super.add_class_to_image("forum_card_image");
        super.set_image_dim(60,60);
        super.set_image_alignment(Pos.TOP_LEFT);
        super.set_label_alignment(Pos.TOP_CENTER);
        
        StackPane displayPane = new StackPane();
        displayPane.setPrefSize(800, 300);
        displayPane.getStyleClass().addAll( "forum_display" );
        Label text = new Label(forum.get_body());
        text.setWrapText(true);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        text.getStyleClass().addAll( "forum_display_text" );
        StackPane.setAlignment(text,Pos.CENTER);
        displayPane.getChildren().addAll(text);
        super.getChildren().addAll(displayPane);
           
    }
    /* no on touch callback */
    
    public Forum get_forum(){
        return card_forum;
    }
    
}
