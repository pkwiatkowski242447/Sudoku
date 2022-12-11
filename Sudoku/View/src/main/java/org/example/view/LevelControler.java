package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import pl.sudoku.BacktrackingSudokuSolver;
import pl.sudoku.SudokuBoard;
import pl.sudoku.SudokuSolver;

import java.util.*;

public class LevelControler {

    @FXML
    private GridPane siatka;
    private SudokuBoard sudokuBoard1;
    private SudokuBoard sudokuBoard2;
    private SudokuSolver sudokuSolver;
    private Difficulty difficulty;
    @FXML
    private Label test;

    public void initialize() {
        sudokuSolver =  new BacktrackingSudokuSolver();
        sudokuBoard1 = new SudokuBoard(sudokuSolver);
        sudokuBoard1.solveGame();
        sudokuBoard2 = sudokuBoard1.clone();

        difficulty = UserActionHandling.getDifficulty();
        removeSomeFields(sudokuBoard2, difficulty);

        fillBoard();
    }

    private void fillBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setMinHeight(50.0);
                textField.setMinWidth(50.0);
                textField.setFont(new Font(40));
                if (sudokuBoard2.get(i,j) != 0) {
                    textField.setDisable(true);
                    textField.setText(String.valueOf(sudokuBoard2.get(i,j)));
                }
                siatka.add(textField,i,j);
            }
        }
    }

    public void removeSomeFields(SudokuBoard sudokuBoard, Difficulty difficulty) {
        int numberOfFieldsToRemove = difficulty.getEnumValue();
        int xValue = 0;
        int yValue = 0;


        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            values.add(i);
        }

        Collections.shuffle(values);

        for (int i = 0; i < numberOfFieldsToRemove; i++) {
            xValue = (int) (values.get(i) / 9);
            yValue = values.get(i) % 9;
            sudokuBoard.set(xValue, yValue, 0);
        }
    }
}