/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Note;

/**
 *
 * @author Aravind
 */
public class Note {
    
    private String note_title;
    private String note_description;
    private String note_icon_path;

    public Note(String title, String description, String icon_path) {
        note_title = title;
        note_description = description;
        note_icon_path = icon_path;
    }
    
    public void set_title(String t_title){
        this.note_title = t_title;
    }
    
    public void set_description(String t_description){
        this.note_description = t_description;
    }
    
    public void set_icon(String t_icon_path){
        this.note_icon_path = t_icon_path;
    }
    
    public String get_title(){
        return note_title;
    }
    
    public String get_description(){
        return note_description;
    }
    
    public String get_icon_path(){
        return note_icon_path;
    }
    
    public void save_note_detail(){
    
        // backend name description icon_path
    }
    /* get assignments files etc */
}
