package org.example.view;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class StageSetup {
    private static Stage stage;

    public static void setStage(Stage stage) {
        StageSetup.stage = stage;
    }

    public static Stage getStage() {
        return stage;
    }

    public static Parent loadFXMLFile(String path, ResourceBundle resourceBundle) throws IOException {
        return new FXMLLoader(StageSetup.class.getResource(path),resourceBundle).load();
    }

    public static void setUpStage(String path, String title,ResourceBundle resourceBundle) throws IOException {
        stage.setTitle(title);
        stage.setScene(new Scene(loadFXMLFile(path,resourceBundle)));
        stage.show();
    }

    public static void setUpStage(Stage stage, String path, String title, ResourceBundle resourceBundle) throws IOException {
        setStage(stage);
        stage.setTitle(title);
        stage.setScene(new Scene(loadFXMLFile(path,resourceBundle)));
        stage.show();
    }

    public static void setUpStage(String path, ResourceBundle resourceBundle) throws IOException {
        setStage(stage);
        stage.setScene(new Scene(loadFXMLFile(path,resourceBundle)));
        stage.show();
    }

}
