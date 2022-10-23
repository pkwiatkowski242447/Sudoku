package pl.main;

import java.lang.System;
import pl.sudoku.BacktrackingSudokuSolver;
import pl.sudoku.SudokuBoard;
import pl.sudoku.SudokuSolver;

public class Main {
    public static void main(String[] args) {
        System.out.println("Projekt - Sudoku.");
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard exampleSudoku = new SudokuBoard(solver);
        exampleSudoku.solveGame();
        System.out.println(exampleSudoku.toString());
    }
}
