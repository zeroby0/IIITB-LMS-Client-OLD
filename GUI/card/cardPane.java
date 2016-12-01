/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.card;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Aravind
 */
public class cardPane {
    
    
    cardPane(){}
    
    public static void make_card_pane(TilePane tile_pane, ScrollPane scroll_pane ){
        
        scroll_pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll_pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll_pane.setFitToHeight(true);
        scroll_pane.setFitToWidth(true);
        
        scroll_pane.getStyleClass().addAll("card_scroll_pane");
        tile_pane.getStyleClass().addAll("card_tile_pane");
        
        tile_pane.setPrefColumns(3);
     
    }
    
}
