package org.example.view;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class Main extends Application {
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("Language");

    @Override
    public void start(Stage stage) throws IOException {
       StageSetup.setUpStage(stage,"main-form.fxml","as",resourceBundle);
    }

    public static void main(String[] args) {
        launch();
    }
}
