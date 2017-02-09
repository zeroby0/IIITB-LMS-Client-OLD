/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.tabs;

import static GUI.util.Util.buildImage;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Aravind
 */
public class tab_helper {
        
    public static  void stylise_tab(String icon_path, Tab tab, AnchorPane overlay ){
        ImageView tab_icon = buildImage(icon_path);
        Label tab_label = new Label();
        tab_label.setGraphic(tab_icon);
        tab.setGraphic( tab_label );
        
        // Hide overlay when clicked
        tab_label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    overlay.setVisible(false);
//                    System.out.println("remove");
               }
            }
        });
        
        
    }
    
    public static void stylise_tab(String icon_path, Tab tab ){
        ImageView tab_icon = buildImage(icon_path);
        Label tab_label = new Label();
        tab_label.setGraphic(tab_icon);
        tab.setGraphic( tab_label );       
    }
    

    
    
    
}
