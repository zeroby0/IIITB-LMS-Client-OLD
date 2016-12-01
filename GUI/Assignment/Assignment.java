/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Assignment;

/**
 *
 * @author Aravind
 */
public class Assignment {
    
    private String title;
    private String deadline;
    private String description;
    private String icon_path;
    private boolean is_done;

    public Assignment(String title, String deadline, String description, String course_id, String icon_path, String is_done) {
        this.title = title;
        this.deadline = deadline;
        this.description = description;
        this.icon_path = icon_path;
        
        this.is_done = is_done.equals("true");
    }
    
    public void set_title(String title){
      this.title = title;  
    }
    
    public String get_title(){
        return this.title;
    }
    
    public void set_deadline(String title){
        this.deadline = deadline;
    }
    
    public String get_deadline(){
        return this.deadline;
    }
    
    public void set_description(String description){
        this.description = description;
    }
    
    public String get_description(){
        return this.description;
    }
    
    public void set_icon(String icon_path){
        this.icon_path = icon_path;
    }
    
    public String get_icon_path(){
        return this.icon_path;
    }
}
