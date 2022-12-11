package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pl.sudoku.SudokuBoard;
import org.example.view.LevelManager;

import java.util.Objects;

public class SceneController {
    @FXML
    Button button_easy = new Button("Easy");
    @FXML
    Button button_medium = new Button("Medium");
    @FXML
    Button button_hard = new Button("Hard");
    private SudokuBoard boardzik;
    static Level level;

    public static Level getLevel() {
        return level;
    }

    public void switchToEasy() throws Exception {
        level = Level.Easy;
        LevelManager.readyBoard();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Board.fxml")));
        LevelManager.fillGridPane();
        Stage window = (Stage) button_easy.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }
    public void switchToMedium() throws Exception {
        level = Level.Medium;
        LevelManager.readyBoard();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Board.fxml")));
        Stage window = (Stage) button_easy.getScene().getWindow();
        LevelManager.fillGridPane();
        window.setScene(new Scene(root,750,500));
    }
    public void switchToHard() throws Exception {
        level = Level.Hard;
        LevelManager.readyBoard();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Board.fxml")));
        LevelManager.fillGridPane();
        Stage window = (Stage) button_easy.getScene().getWindow();
        window.setScene(new Scene(root,750,500));
    }



}