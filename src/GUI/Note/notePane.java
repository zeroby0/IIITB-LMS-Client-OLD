/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Note;

import GUI.card.cardPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Aravind
 */
public class notePane {
    // make given scroll pane and tile pane to note tab
    public static void make_note_pane(ScrollPane scroll_pane, TilePane tile_pane){
        
        cardPane.make_card_pane(tile_pane, scroll_pane);
        scroll_pane.getStyleClass().addAll("notes_scroll_pane");
        tile_pane.getStyleClass().addAll("notes_tile_pane");
    }
    
    // populate note pane with notes
    public static void populate_pane(TilePane tile_pane, Note[] list_of_notes, AnchorPane overlay){
        
        for (Note nt: list_of_notes) {
                noteCard card = new noteCard(nt, overlay);
                card.set_on_touch_callback_default( );
                tile_pane.getChildren().add( card );
        }
    }

}
