/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Aravind
 */
public class Util {
    public static ImageView buildImage(String path){
        String fullpath = String.format("%s", System.getProperty("user.dir"));
        Image img = new Image( "file:" + fullpath + "/" + path );
        
        ImageView imv = new ImageView();
        imv.setImage( img );
        
        imv.setFitHeight(30);
        imv.setFitWidth(30);
        imv.setVisible(true);
        
        imv.setPreserveRatio(true);
        imv.setSmooth(true); // what does this actually do?
        
        imv.setRotate(90);
        
        
        imv.getStyleClass().addAll("tab_image");
        return imv;
        
    }
    
}
