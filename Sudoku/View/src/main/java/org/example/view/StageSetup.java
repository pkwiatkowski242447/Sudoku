package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class StageSetup {
    private static Stage primaryStage;

    public static Stage getStage() {
        return primaryStage;
    }

    private static void setStage(Stage stage) {
        StageSetup.primaryStage = stage;
    }

    public static Parent fxmlFileLoad(String fxmlFromPath, ResourceBundle bundle) throws IOException {
        return new FXMLLoader(StageSetup.class.getResource(fxmlFromPath), bundle).load();
    }

    public static void buildStage(String fxmlFormPath, ResourceBundle bundle) throws IOException {
        primaryStage.setScene(new Scene(fxmlFileLoad(fxmlFormPath, bundle)));
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void buildStage(String fxmlFormPath, String title, ResourceBundle bundle) throws IOException {
        primaryStage.setScene(new Scene(fxmlFileLoad(fxmlFormPath, bundle)));
        primaryStage.setTitle(title);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    public static void buildStage(Stage stage, String fxmlFormPath, String title, ResourceBundle bundle) throws IOException {
        setStage(stage);
        primaryStage.setScene(new Scene(fxmlFileLoad(fxmlFormPath, bundle)));
        primaryStage.setTitle(title);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
