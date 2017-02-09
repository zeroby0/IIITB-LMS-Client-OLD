/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Course;

/**
 *
 * @author Aravind
 */
public class Course {
    
    private String course_title;
    private String course_instructor;
    private String course_icon_path;
    private String course_id;

    public Course(String title, String instructor, String icon_path, String id) {
        course_title = title;
        course_instructor = instructor;
        course_icon_path = icon_path;
        course_id = id;
    }
    
    public void set_title(String t_title){
        this.course_title = t_title;
    }
    
    public void set_instructor(String t_instructor){
        this.course_instructor = t_instructor;
    }
    
    public void set_icon(String t_icon_path){
        this.course_icon_path = t_icon_path;
    }
    
    public String get_title(){
        return course_title;
    }
    
    public String get_instructor(){
        return course_instructor;
    }
    
    public String get_icon_path(){
        return course_icon_path;
    }
    
    public String get_course_id(){
        return course_id;
    }
    
    public void save_course_detail(){
    
        // backend name instructor icon_path
    }
    /* get assignments files etc */
}
