package org.example.view;

import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import java.io.IOException;
import java.util.ResourceBundle;

public class Main extends Application {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");

    @Override
    public void start(Stage stage) throws IOException {
        StageSetup.buildStage(stage,"main-form.fxml",
                resourceBundle.getString("gameTitle"), resourceBundle);
    }

    public static void main(String[] args) {
        launch();
    }
}
