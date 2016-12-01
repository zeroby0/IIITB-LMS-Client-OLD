/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Note;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Aravind
 */
public class note_pane_helper{
    static public void setup_note_pane(ScrollPane scroll_pane, TilePane tile_pane, AnchorPane overlay ){
    
        Note[] list_of_notes = new Note[7];
        
        list_of_notes[0] = new Note("Assign 1", "M rao", "src/GUI/resources/main/writing.png");
        list_of_notes[1] = new Note("Assign 1", "M rao", "src/GUI/resources/main/networking.png");
        list_of_notes[2] = new Note("Assign 1", "M rao", "src/GUI/resources/main/group.png");
        list_of_notes[3] = new Note("Assign 1", "M rao", "src/GUI/resources/main/writing.png");
        list_of_notes[4] = new Note("Assign 1", "M rao", "src/GUI/resources/main/group.png");
        list_of_notes[5] = new Note("Assign 1", "M rao", "src/GUI/resources/main/networking.png");
        list_of_notes[6] = new Note("Assign 1", "M rao", "src/GUI/resources/main/writing.png");
        
        
        
        notePane.make_note_pane(scroll_pane, tile_pane);
        notePane.populate_pane(tile_pane, list_of_notes, overlay);
    }

}
