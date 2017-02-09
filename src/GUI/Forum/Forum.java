/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Forum;

/**
 *
 * @author Aravind
 */
public class Forum {
    
    final String forum_title;
    final String forum_body;
    final String forum_files;
    final String forum_id;
    final String forum_course_id;
    final String forum_time;
    final String forum_type;
    
    final String forum_icon_path;
    
    public Forum(String title, String body, String files, String id, String course_id, String time, String type ){
        this.forum_title = title;
        this.forum_body = body;
        this.forum_course_id = course_id;
        this.forum_id = id;
        this.forum_type= type;
        this.forum_time = time;
        this.forum_files = files;
        
        this.forum_icon_path = "src/GUI/resources/main/group.png"; /* make general or news based on type */

        
    }
    
    public String get_title(){
        return forum_title;
    }
    
    public String get_time(){
        return forum_time;
    }
    
    public String get_files(){
        return forum_files;
    }
    
    public String get_body(){
        return forum_body;
    }
    
    public String get_id(){
        return forum_id;
    }
    
    public String get_course_id(){
        return forum_course_id;
    }
    
    public String get_type(){
        return forum_type;
    }
    
    public String get_icon_path(){
        return forum_icon_path;
    }
    
}
