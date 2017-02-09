/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Assignment;

import Backend.Backend;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

/**
 *
 * @author Aravind
 */
public class assignment_pane_helper{
    
    static public void setup_assignment_pane(ScrollPane scroll_pane, TilePane tile_pane, AnchorPane overlay){
        

        ArrayList<Assignment> list_of_assignments = new ArrayList<Assignment>(); /* backend get List of courses */
      
        
        List<HashMap<String,String>> backend_assignment_list = Backend.sortedassignments();
//        System.out.println(backend_assignment_list);
        String[] assignment_icon_list = {"src/GUI/resources/main/assignment_logo.png"};
        Random r = new Random();
        int current_year =  Calendar.getInstance().get(Calendar.YEAR);
        int current_month = Calendar.getInstance().get(Calendar.MONTH);

        
        
        for (HashMap<String, String> entry : backend_assignment_list) {
 
            String Assignment_deadline = entry.get("DUEBY");
            
            // remove old assignments, whose deadlines are over a month old
            if( Integer.parseInt( Assignment_deadline.substring(0, 4) ) < current_year ){
                continue;
            }else if( Integer.parseInt( Assignment_deadline.substring(5, 5+2) ) < current_month ){
                continue;
            }
            
            if( Integer.parseInt( Assignment_deadline.substring(0, 4) ) > current_year + 1 ){
                Assignment_deadline = "No due-date";
            }
            
            
            
            String Assignment_name = entry.get("NAME");
            String Assignment_course_id = entry.get("COURSEID");
            String Assignment_icon = assignment_icon_list[ r.nextInt(assignment_icon_list.length) ];

            String Assignment_description = entry.get("description");
            String Assignment_status = entry.get("DONE");

            Assignment asn = new Assignment(Assignment_name, Assignment_deadline,  "Assignment sjh", Assignment_course_id, Assignment_icon, Assignment_status);
            list_of_assignments.add(asn);
        }
        

        assignmentPane.make_assignment_pane(scroll_pane, tile_pane);
        assignmentPane.populate_pane(tile_pane, list_of_assignments, overlay);
    
    }

}

