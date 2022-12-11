package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stageNo1) throws IOException {
       Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("First.fxml")));
       stageNo1.setTitle("Wybranie poziomu trudnosci");
       stageNo1.setScene(new Scene(root,750,500));
       stageNo1.show();
    }

    public static void main(String[] args) {
        launch();
    }
}