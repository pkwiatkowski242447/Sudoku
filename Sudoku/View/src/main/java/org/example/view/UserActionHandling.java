package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserActionHandling {
    @FXML
    private Label noDiffSelected;
    @FXML
    private ChoiceBox diffLevel;
    private static Difficulty difficulty;

    @FXML
    private ChoiceBox language_choice;
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("Language");
    private String choice;
    @FXML
    private Label noLanguageSelected;

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    @FXML
    protected void playGame(ActionEvent actionEvent) throws IOException {
        String input = diffLevel.getSelectionModel().getSelectedItem().toString();
        difficulty = Difficulty.toRealDiff(input);
        if (difficulty != null) {
            StageSetup.setUpStage("game-view.fxml",resourceBundle);
        } else {
            noDiffSelected.setText("Nie wybrano poziomu trudności.");
        }
    }
    @FXML
    protected void chooseLanguage(ActionEvent actionEvent) throws IOException {
        choice = language_choice.getSelectionModel().getSelectedItem().toString();
        if(choice.equals(resourceBundle.getString("PL"))) {
            Locale.setDefault(new Locale("pl","PL"));
        }
        else if(choice.equals(resourceBundle.getString("EN"))) {
            Locale.setDefault(new Locale("en", "EN"));
        }
        else {
            noLanguageSelected.setText("Nie wybrano języka");
        }
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Language");
        StageSetup.setUpStage("game-view.fxml",resourceBundle);
    }
    @FXML
    protected void saveToFile(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.txt");

    }
}
