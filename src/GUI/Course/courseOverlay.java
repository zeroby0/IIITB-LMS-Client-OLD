/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Course;

import Backend.Backend;
import GUI.Forum.Forum;
import GUI.Forum.forumPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Aravind
 */
public class courseOverlay {
    static AnchorPane overlay_pane;
    static AnchorPane overlay_header;
    static Label overlay_label_title;
    static ScrollPane overlay_scrollpane;
    static TilePane overlay_contentpane;
    static String course_id;
    
    courseOverlay(){
        
    }
    
    public static void populate_overlay(Course crs){
        
        
        List<HashMap<String,String>> backend_forum_list = Backend.sortedforumposts( crs.get_course_id() );
//        System.out.println(backend_forum_list.size());
//        
        String[] forum_icon_list = {"src/GUI/resources/main/assignment_logo.png"};
        
        ArrayList<Forum> list_of_forums = new ArrayList<Forum>();

        
        for (HashMap<String, String> entry : backend_forum_list) {

            String forum_title  = entry.get("NAME");
            String forum_body = entry.get("CONTENT");
            String forum_file = entry.get("FILES");
            String forum_id = entry.get("POSTID");
            String forum_course = entry.get("COURSEID");
            String forum_time = entry.get("TIME");
            String forum_type = entry.get("TYPE");
            
            Forum forum = new Forum(forum_title, forum_body, forum_file, forum_id, forum_course, forum_time, forum_type );
            list_of_forums.add(forum);
            
        }
//        System.out.println(list_of_forums.size());
        forumPane.populate_pane(overlay_contentpane, list_of_forums);
        
        
        
        
        
    
    }
    
    public static void set_overlay_pane(AnchorPane pane){
        overlay_pane = pane;
        pane.getStyleClass().addAll("forum_tile_pane");
    }
    
    public static void set_header(AnchorPane header){
        overlay_pane = header;
    }
    
    public static void set_label(Label lbl){
        overlay_label_title = lbl;
    }
    
    public static void set_scrollpane(ScrollPane scrl){
        overlay_scrollpane = scrl;
    }
    
    public static void set_tilepane(TilePane tlpn){
        overlay_contentpane = tlpn;
    }
    
    public static Label get_label(){
        return overlay_label_title;
    }
    
    
    
    
    
}
