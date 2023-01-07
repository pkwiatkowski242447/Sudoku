package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import pl.sudoku.Dao;
import pl.sudoku.JdbcSudokuBoardDao;
import pl.sudoku.SudokuBoard;
import pl.sudoku.exceptions.GeneralDaoException;
import pl.sudoku.exceptions.InputOutputOperationException;
import static pl.sudoku.SudokuBoardDaoFactory.getFileDao;
import static pl.sudoku.SudokuBoardDaoFactory.getJdbcDao;

public class UserActionHandling {

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("ViewBundle");
    @FXML
    private ChoiceBox diffLevel;
    @FXML
    private ChoiceBox availableBoards;
    @FXML
    private Label noDiffSelected;
    @FXML
    private Label firstAuthor;
    @FXML
    private Label secondAuthor;
    private static SudokuBoard userStartBoard;
    private static SudokuBoard filledPartiallyBoard;

    public static SudokuBoard getUserStartBoard() {
        return userStartBoard;
    }

    public static SudokuBoard getFilledPartiallyBoard() {
        return filledPartiallyBoard;
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
        String boardSelection = availableBoards.getSelectionModel().getSelectedItem().toString();
        String input = diffLevel.getSelectionModel().getSelectedItem().toString();
        difficulty = Difficulty.toRealDiff(input);
        if (!boardSelection.isEmpty()
                && !boardSelection.equals(resourceBundle.getString("selectBoard"))) {
            try (JdbcSudokuBoardDao databaseDao = (JdbcSudokuBoardDao) getJdbcDao()) {
                databaseDao.setBoardName(boardSelection);
                System.out.println(boardSelection);
                userStartBoard = databaseDao.read();
                System.out.println("_" + boardSelection);
                databaseDao.setBoardName("_" + boardSelection);
                filledPartiallyBoard = databaseDao.read();
            } catch (GeneralDaoException daoException) {
                daoException.printStackTrace();
            }
            StageSetup.buildStage("game-view.fxml", resourceBundle);
        } else if (difficulty != null) {
            StageSetup.buildStage("game-view.fxml", resourceBundle);
        } else {
            noDiffSelected.setText(resourceBundle.getString("noDiffSelected"));
        }
    }

    private void loadData() {
        List<String> boardNames = new ArrayList<>();
        List<String> options = new ArrayList<>();
        diffLevel.getItems().addAll(
                resourceBundle.getString("diffLevelEasy"),
                resourceBundle.getString("diffLevelMedium"),
                resourceBundle.getString("diffLevelHard"));
        try (JdbcSudokuBoardDao databaseDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            boardNames = databaseDao.retrieveUsedBoardNames();
        } catch (GeneralDaoException daoException) {
            daoException.printStackTrace();
        }
        for (String value : boardNames) {
            if (!value.startsWith("_")) {
                options.add(value);
            }
        }
        availableBoards.getItems().addAll(options);
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
            userStartBoard = fileDao.read();
            filledPartiallyBoard = fileDao.read();
            StageSetup.buildStage("game-view.fxml", resourceBundle1);
        } catch (InputOutputOperationException ex) {
            throw new GeneralDaoException(ex.getMessage(), ex.getCause());
        }
    }
}
