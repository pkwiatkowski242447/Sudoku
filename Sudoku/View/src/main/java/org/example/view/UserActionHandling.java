package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserActionHandling {

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");

    @FXML
    private ChoiceBox diffLevel;
    @FXML
    private Label noDiffSelected;
    @FXML
    private String language;
    @FXML
    private ChoiceBox langSetting;
    @FXML
    private Label noLangSelected;
    @FXML
    private Label firstAuthor;
    @FXML
    private Label secondAuthor;
    private static Difficulty difficulty;

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    public String getLanguage() {
        return language;
    }

    @FXML
    public void initialize() {
        loadData();
    }

    @FXML
    protected void playGame() throws IOException {
        String input = diffLevel.getSelectionModel().getSelectedItem().toString();
        difficulty = Difficulty.toRealDiff(input);
        if (difficulty != null) {
            StageSetup.buildStage("game-view.fxml", resourceBundle);
        } else {
            noDiffSelected.setText(resourceBundle.getString("noDiffSelected"));
        }
    }

    private void loadData() {
        diffLevel.getItems().addAll(
                resourceBundle.getString("diffLevelEasy"),
                resourceBundle.getString("diffLevelMedium"),
                resourceBundle.getString("diffLevelHard"));
        langSetting.getItems().addAll(
                resourceBundle.getString("PL"),
                resourceBundle.getString("EN")
        );
    }

    @FXML
    public void selectLanguage() throws IOException {
        this.language = langSetting.getSelectionModel().getSelectedItem().toString();

        if (language.equals(resourceBundle.getString("PL"))) {
            Locale.setDefault(new Locale("pl","PL"));
        } else if (language.equals(resourceBundle.getString("EN"))) {
            Locale.setDefault(new Locale("en","EN"));
        }
        resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        StageSetup.buildStage("main-form.fxml",
                resourceBundle.getString("gameTitle"), resourceBundle);
    }

    @FXML
    public void showAuthors() {
        ResourceBundle authorBundle = ResourceBundle.getBundle("org.example.view.Authors");

        if (firstAuthor.getText().isEmpty() && secondAuthor.getText().isEmpty()) {
            firstAuthor.setText(authorBundle.getString("firstAuth"));
            secondAuthor.setText(authorBundle.getString("secondAuth"));
        } else {
            firstAuthor.setText("");
            secondAuthor.setText("");
        }
    }

    @FXML
    public void readFromAFile() throws Exception {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(resourceBundle.getString("fileRead"));
        dialog.setContentText(resourceBundle.getString("fileCtxText"));
        dialog.setHeaderText(resourceBundle.getString("fileRead"));
        dialog.show();
        String path = dialog.getEditor().getText();
        /*
        try (Dao<SudokuBoard> someDao = getFileDao(path)) {

        } catch () {

        }

         */
    }
}
