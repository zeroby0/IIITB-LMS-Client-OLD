/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

import GUI.card.cardPane;
import java.util.ArrayList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Aravind
 */
public class forumPane {
    public static void make_forum_pane(ScrollPane scroll_pane, TilePane tile_pane){
        cardPane.make_card_pane(tile_pane, scroll_pane);
        scroll_pane.getStyleClass().addAll("forum_scroll_pane");
        tile_pane.getStyleClass().addAll("forum_tile_pane");
    }
    
    public static void populate_pane(TilePane tile_pane, ArrayList<Forum> list_of_forums){
        tile_pane.getChildren().clear();
        for(Forum frm: list_of_forums){
            forumCard card = new forumCard(frm);
            
            tile_pane.getChildren().add( card );
        }
        
    }
    
}
