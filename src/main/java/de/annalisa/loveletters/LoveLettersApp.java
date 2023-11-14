package de.annalisa.loveletters;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoveLettersApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoveLettersApp.fxml"));
            Parent root = loader.load();

            // Create a scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add("loveLetters.css");
            primaryStage.setScene(scene);
            primaryStage.setTitle("Love Letters Game");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
