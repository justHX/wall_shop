/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wall_shop;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author hulk-
 */
public class Wall_Shop extends Application {
    
    @Override
    public void start(Stage stage) throws Exception { 
        Parent root = FXMLLoader.load(getClass().getResource("/FormsFXML/MainForm.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Wallpapers Shop VERSION betta 0.8.3");
        stage.getIcons().add(new Image("/ICONS/iconWall.png"));
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
