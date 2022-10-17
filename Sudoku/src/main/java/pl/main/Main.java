package pl.main;

import java.lang.System;
import pl.sudoku.SudokuBoard;

public class Main {
    public static void main(String[] args) {
        System.out.println("Projekt - Sudoku.");
        SudokuBoard exampleSudoku = new SudokuBoard();
        exampleSudoku.fillBoard();
        System.out.println(exampleSudoku.showSudokuBoard());
    }
}
