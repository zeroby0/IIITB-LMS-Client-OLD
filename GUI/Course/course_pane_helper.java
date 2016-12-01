/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Course;

import Backend.Backend;
import java.util.ArrayList;
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
// class courses tab helper commands
public class course_pane_helper{
    
    static public void setup_course_pane(ScrollPane scroll_pane, TilePane tile_pane, AnchorPane overlay ){
        
        ArrayList<Course> list_of_courses = new ArrayList<Course>(); /* backend get List of courses */
            
        /* get Courses info from backend */
        List<HashMap<String,String>> backend_course_list = Backend.getcourses();
        System.out.println(backend_course_list);
        String[] course_icon_list = {"src/GUI/resources/main/courseIcons/cpp.png", "src/GUI/resources/main/courseIcons/chemistry.png", "src/GUI/resources/main/courseIcons/electronics.png", "src/GUI/resources/main/courseIcons/java.png", "src/GUI/resources/main/courseIcons/biology.png"};
        Random r = new Random();
        
        for (HashMap<String, String> entry : backend_course_list) {
            
            String course_name = entry.get("NAME");
            String course_id = entry.get("COURSEID");
            String course_icon = course_icon_list[ r.nextInt(course_icon_list.length) ];
            System.out.println(course_icon);
            System.out.println(course_name);
            Course crs = new Course(course_name, "Instructor",  course_icon, course_id );
            list_of_courses.add(crs);
        
        }
            
        coursePane.make_course_pane(scroll_pane, tile_pane);
        coursePane.populate_pane(tile_pane, list_of_courses, overlay);
       
        /* set Tab icons */
    
    }
    
}
