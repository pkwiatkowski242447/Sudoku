package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class UserActionHandling {
    @FXML
    private Label noDiffSelected;
    @FXML
    private ChoiceBox diffLevel;
    private static Difficulty difficulty;

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    @FXML
    protected void playGame(ActionEvent actionEvent) throws IOException {
        String input = diffLevel.getSelectionModel().getSelectedItem().toString();
        difficulty = Difficulty.toRealDiff(input);
        if (difficulty != null) {
            Parent root = FXMLLoader.load(getClass().getResource("game-view.fxml"));
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            noDiffSelected.setText("Nie wybrano poziomu trudności.");
        }
    }
}
