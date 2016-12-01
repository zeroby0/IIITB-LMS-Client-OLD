/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Course;

import GUI.card.cardPane;

import java.util.ArrayList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

public class coursePane {
    Course active_course;

    // makes a pane for course card display
    public static void make_course_pane(ScrollPane scroll_pane, TilePane tile_pane){
        
        cardPane.make_card_pane(tile_pane, scroll_pane);
        scroll_pane.getStyleClass().addAll("course_scroll_pane");
        tile_pane.getStyleClass().addAll("course_tile_pane");
        
    }
    
    // populate course pane with course cards
    public static void populate_pane(TilePane tile_pane, ArrayList<Course> list_of_courses, AnchorPane overlay){
        
        list_of_courses.stream().map((crs) -> new courseCard(crs, overlay)).map((card) -> {
            card.set_on_touch_callback_default( );
            return card;
        }).forEachOrdered((card) -> {
            tile_pane.getChildren().add( card );
        });
    }
    
    
    
    
    


}
