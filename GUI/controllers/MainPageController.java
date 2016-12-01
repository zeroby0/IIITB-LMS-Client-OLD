/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.controllers;


import GUI.Assignment.assignment_pane_helper;
import GUI.Course.courseOverlay;
import GUI.Course.course_pane_helper;
import GUI.Forum.forumPane;
import GUI.Note.note_pane_helper;
import GUI.applicationManager;
import static GUI.tabs.tab_helper.stylise_tab;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;




/**
 * FXML Controller class
 *
 * @author Aravind
 */


public class MainPageController implements Initializable {
    
    @FXML AnchorPane base_pane;
        
    
    
    @FXML Tab notes_tab;
    @FXML Tab files_tab;
    @FXML Tab settings_tab;
    
    /* courses */
    @FXML Tab course_tab;
    @FXML AnchorPane course_tab_pane;
    
    @FXML AnchorPane course_header_pane;
    @FXML ScrollPane course_scroll_pane;
    @FXML TilePane course_tile_pane;
      
    @FXML AnchorPane course_overlay_pane;
    @FXML AnchorPane coursepane_overlay_header;
    @FXML Label coursepane_overlay_title;
    @FXML ScrollPane coursepane_overlay_scrollpane;
    @FXML TilePane coursepane_overlay_contentpane;
    
    /* courses end */
    
    
    
    
    /* Assignment */
    @FXML Tab assignment_tab;
    
    @FXML AnchorPane assignments_header_pane;
    
    @FXML ScrollPane assignments_scroll_pane;
    @FXML TilePane assignments_tile_pane;
    
    @FXML AnchorPane assignment_overlay_pane;
    /* Assignment end */
    
    @FXML ScrollPane notes_scroll_pane;
    @FXML TilePane notes_tile_pane;

 
    private void setup_tabs(){
        
        course_overlay_pane.setVisible(false);
        course_overlay_pane.setManaged(false);
        assignment_overlay_pane.setVisible(false);
        assignment_overlay_pane.setManaged(false);

        stylise_tab( "src/GUI/resources/main/course.png", course_tab, course_overlay_pane );
        stylise_tab( "src/GUI/resources/main/assignment.png", assignment_tab,  assignment_overlay_pane);
        stylise_tab( "src/GUI/resources/main/inbox.png", notes_tab);
        stylise_tab( "src/GUI/resources/main/folder.png", files_tab);
        stylise_tab( "src/GUI/resources/main/settings.png", settings_tab);

        
    }
    
    /**
     *
     * @param appManager
     */
    public void setup_page(applicationManager appManager){
        
        Runnable overlay_task = () -> {

                forumPane.make_forum_pane(coursepane_overlay_scrollpane, coursepane_overlay_contentpane);
                courseOverlay.set_overlay_pane(course_overlay_pane);
                courseOverlay.set_header(course_header_pane);
                courseOverlay.set_label(coursepane_overlay_title);
                courseOverlay.set_scrollpane(coursepane_overlay_scrollpane);
                courseOverlay.set_tilepane(coursepane_overlay_contentpane);

            Platform.runLater(() -> {
            });

        };


        new Thread(overlay_task).start();
        
        
        System.out.println("setup_page mainPageController");
        course_pane_helper.setup_course_pane(course_scroll_pane, course_tile_pane, course_overlay_pane);
        assignment_pane_helper.setup_assignment_pane(assignments_scroll_pane, assignments_tile_pane, assignment_overlay_pane);
        note_pane_helper.setup_note_pane(notes_scroll_pane, notes_tile_pane, course_overlay_pane);
        

        
        FadeTransition ft = new FadeTransition(Duration.millis(800), base_pane);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        
        Label lb = new Label();
        
        setup_tabs();

    }
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
