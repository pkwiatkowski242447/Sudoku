package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

import java.io.IOException;

public class Main extends Application {

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-form.fxml"), resourceBundle);
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle(resourceBundle.getString("gameTitle"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
