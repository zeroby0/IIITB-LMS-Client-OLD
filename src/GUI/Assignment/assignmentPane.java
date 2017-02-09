/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Assignment;

import GUI.card.cardPane;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Aravind
 */
public class assignmentPane {

    public static void make_assignment_pane(ScrollPane scroll_pane, TilePane tile_pane){
        
        cardPane.make_card_pane(tile_pane, scroll_pane);
        scroll_pane.getStyleClass().addAll("assignment_scroll_pane");
        tile_pane.getStyleClass().addAll("assignment_tile_pane");
    }
    
    public static void populate_pane(TilePane tile_pane,  ArrayList<Assignment> list_of_assignments, AnchorPane overlay){
        
        for (Assignment asn : list_of_assignments) {
                assignmentCard card = new assignmentCard(asn, overlay);
                card.set_on_touch_callback_default( );
                tile_pane.getChildren().add( card );
        }
    }

}
