package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import pl.sudoku.Dao;
import pl.sudoku.SudokuBoard;
import pl.sudoku.exceptions.GeneralDaoException;
import pl.sudoku.exceptions.InputOutputOperationException;
import static pl.sudoku.SudokuBoardDaoFactory.getFileDao;

public class UserActionHandling {

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("ViewBundle");

    @FXML
    private ChoiceBox diffLevel;
    @FXML
    private Label noDiffSelected;
    @FXML
    private Label firstAuthor;
    @FXML
    private Label secondAuthor;

    private static SudokuBoard fullSudokuBoard;

    private static SudokuBoard userStartBoard;

    private static SudokuBoard filledPartiallyBoard;

    public static SudokuBoard getFullSudokuBoard() {
        return fullSudokuBoard;
    }

    public static SudokuBoard getUserStartBoard() {
        return userStartBoard;
    }

    public static SudokuBoard getFilledPartiallyBoard() {
        return filledPartiallyBoard;
    }

    public static void setFullSudokuBoard(SudokuBoard fullSudokuBoard) {
        UserActionHandling.fullSudokuBoard = fullSudokuBoard;
    }

    public static void setUserStartBoard(SudokuBoard userStartBoard) {
        UserActionHandling.userStartBoard = userStartBoard;
    }

    public static void setFilledPartiallyBoard(SudokuBoard filledPartiallyBoard) {
        UserActionHandling.filledPartiallyBoard = filledPartiallyBoard;
    }

    private static Difficulty difficulty;

    public static Difficulty getDifficulty() {
        return difficulty;
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
    }

    @FXML
    public void switchToPL() throws IOException {
        Locale.setDefault(new Locale("pl", "PL"));
        resourceBundle = ResourceBundle.getBundle("ViewBundle");
        StageSetup.buildStage("main-form.fxml",
                resourceBundle.getString("gameTitle"), resourceBundle);
    }

    @FXML
    public void switchToEN() throws IOException {
        Locale.setDefault(new Locale("en", "EN"));
        resourceBundle = ResourceBundle.getBundle("ViewBundle");
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
        ResourceBundle resourceBundle1 = ResourceBundle.getBundle("ViewBundle");
        String pathToFile;
        FileChooser chooseFile = new FileChooser();
        pathToFile = chooseFile.showOpenDialog(StageSetup.getStage()).getAbsolutePath();
        try (Dao<SudokuBoard> fileDao = getFileDao(pathToFile)) {
            fullSudokuBoard = fileDao.read();
            userStartBoard = fileDao.read();
            filledPartiallyBoard = fileDao.read();
            StageSetup.buildStage("game-view.fxml", resourceBundle1);
        } catch (InputOutputOperationException ex) {
            throw new GeneralDaoException(ex.getMessage(), ex.getCause());
        }
    }
}
