package org.example.view;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import pl.sudoku.SudokuBoard;
import pl.sudoku.SudokuSolver;
import pl.sudoku.BacktrackingSudokuSolver;

public class LevelManager {
    static SudokuSolver solverek = new BacktrackingSudokuSolver();
    static SudokuBoard exampleBoard = new SudokuBoard(solverek);
    static LevelManager levelek = new LevelManager();
    private static GridPane exampleBoardGridPane = new GridPane();
    public SudokuBoard levelDepedency(SudokuBoard boardzik, Level poziom) {
        int rand_1 = 0;
        int rand_2 = 0;
        int max = 0;
        if (poziom.getValue() == 40) {
            max = 40;
            if (max > 0) {
                for (int i = 0; i < 81; i++) {
                    rand_1 = (int) Math.floor(Math.random() * (80 - 0 + 1) + 0);
                    rand_2 = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
                    exampleBoard.set(rand_1, rand_2, 0);
                    max++;
                }
            }
        }
        if (poziom.getValue() == 50) {
            max = 50;
            if (max > 0) {
                for (int i = 0; i < 81; i++) {
                    rand_1 = (int) Math.floor(Math.random() * (80 + 1));
                    rand_2 = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
                    exampleBoard.set(rand_1, rand_2, 0);
                    max++;
                }
            }
        }
        if (poziom.getValue() == 60) {
            max = 60;
            if (max > 0) {
                for (int i = 0; i < 81; i++) {
                    rand_1 = (int) Math.floor(Math.random() * (80 + 1));
                    rand_2 = (int) Math.floor(Math.random() * (9 - 1 + 1) + 1);
                    exampleBoard.set(rand_1, rand_2, 0);
                    max++;
                }
            }
        }
        return exampleBoard;
    }
    public static void readyBoard() {
        exampleBoard.solveGame();
        levelek.levelDepedency(exampleBoard, SceneController.getLevel());
    }

    public static void fillGridPane() {
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                TextField textField = new TextField();
                if(exampleBoard.get(i,j) != 0) {
                    textField.setDisable(true);
                    textField.setText(String.valueOf(exampleBoard.get(i,j)));
                }
                exampleBoardGridPane.add(textField,i,j);
            }
        }
    }
}
