package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import pl.sudoku.*;
import pl.sudoku.exceptions.FileSudokuBoardDaoOutputException;
import pl.sudoku.exceptions.GeneralDaoException;
import java.io.File;
import java.util.*;

import static pl.sudoku.SudokuBoardDaoFactory.getFileDao;


public class LevelController {

    @FXML
    private GridPane siatka;
    @FXML
    private Label testLabel;
    private SudokuBoard sudokuBoard1;
    private SudokuBoard sudokuBoard2;
    private SudokuBoard sudokuBoard3;
    private SudokuSolver sudokuSolver;
    private Difficulty difficulty;

    public void initialize() {
        if (UserActionHandling.getFullSudokuBoard() == null
                || UserActionHandling.getUserStartBoard() == null
                || UserActionHandling.getFilledPartiallyBoard() == null) {
            sudokuSolver =  new BacktrackingSudokuSolver();
            sudokuBoard1 = new SudokuBoard(sudokuSolver);
            sudokuBoard1.solveGame();
            sudokuBoard2 = sudokuBoard1.clone();

            difficulty = UserActionHandling.getDifficulty();
            difficulty.removeSomeFields(sudokuBoard2);
            sudokuBoard3 = sudokuBoard2.clone();

            fillBoard();
        } else {
            sudokuBoard1 = UserActionHandling.getFullSudokuBoard();
            sudokuBoard2 = UserActionHandling.getUserStartBoard();
            sudokuBoard3 = UserActionHandling.getFilledPartiallyBoard();

            loadBoards();
        }
    }

    private void fillBoard() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Field field = new Field(i, j, new TextField());
                field.getFieldValue().setMaxHeight(50.0);
                field.getFieldValue().setMaxWidth(50.0);
                field.getFieldValue().setFont(new Font(20));
                if (sudokuBoard2.get(i, j) != 0) {
                    field.getFieldValue().setDisable(true);
                    field.getFieldValue().setText(String.valueOf(sudokuBoard2.get(i, j)));
                }
                field.getFieldValue().textProperty().addListener((observableValue, prevVal, nextVal) -> {
                    if (nextVal.matches("[1-9]") & nextVal.length() == 1) {
                        int fieldValue = Integer.parseInt(field.getFieldValue().getText());
                        int xCoordinate = field.getXValue();
                        int yCoordinate = field.getYValue();

                        sudokuBoard2.set(xCoordinate, yCoordinate, fieldValue);

                        if (sudokuBoard1.get(xCoordinate, yCoordinate)
                                != sudokuBoard2.get(xCoordinate, yCoordinate)) {
                            testLabel.setText(resourceBundle.getString("incorrectFieldValue"));
                        } else {
                            testLabel.setText("");
                        }
                    } else {
                        field.getFieldValue().setText("");
                    }
                });
                siatka.add(field.getFieldValue(),j,i);
            }
        }
    }

    private void loadBoards() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Field field = new Field(i, j, new TextField());
                field.getFieldValue().setMaxHeight(50.0);
                field.getFieldValue().setMaxWidth(50.0);
                field.getFieldValue().setFont(new Font(20));
                if (sudokuBoard3.get(i, j) != 0) {
                    field.getFieldValue().setDisable(true);
                    field.getFieldValue().setText(String.valueOf(sudokuBoard3.get(i,j)));
                } else {
                    if (sudokuBoard2.get(i,j) != 0) {
                        field.getFieldValue().setText(String.valueOf(sudokuBoard2.get(i,j)));
                    }
                    field.getFieldValue().textProperty().addListener((observableValue, prevVal, nextVal) -> {
                        if (nextVal.matches("[1-9]") & nextVal.length() == 1) {
                            int fieldValue = Integer.parseInt(field.getFieldValue().getText());
                            int xCoordinate = field.getXValue();
                            int yCoordinate = field.getYValue();

                            sudokuBoard2.set(xCoordinate, yCoordinate, fieldValue);

                            if (sudokuBoard1.get(xCoordinate, yCoordinate)
                                    != sudokuBoard2.get(xCoordinate, yCoordinate)) {
                                testLabel.setText(resourceBundle.getString("incorrectFieldValue"));
                            } else {
                                testLabel.setText("");
                            }
                        } else {
                            field.getFieldValue().setText("");
                        }
                    });
                }
                siatka.add(field.getFieldValue(), j, i);
            }
        }
    }

    @FXML
    public void saveToFile() throws Exception {
        FileChooser chooseFile = new FileChooser();
        File file = chooseFile.showSaveDialog(StageSetup.getStage());
        try(Dao<SudokuBoard> fileFileDao = getFileDao(file.getAbsolutePath())) {
            fileFileDao.write(sudokuBoard1);
            System.out.println("SudokuBoard1: " + sudokuBoard1);
            fileFileDao.write(sudokuBoard2);
            System.out.println("SudokuBoard2: " + sudokuBoard2);
            fileFileDao.write(sudokuBoard3);
            System.out.println("SudokuBoard3: " + sudokuBoard3);
        } catch (FileSudokuBoardDaoOutputException ex) {
            throw new GeneralDaoException(ex.getMessage(), ex.getCause());
        }
    }
}