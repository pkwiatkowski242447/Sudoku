/*
package pl.main;
import java.lang.System;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.BacktrackingSudokuSolver;
import pl.sudoku.NonAutomaticBoardChangeObserver;
import pl.sudoku.Observer;
import pl.sudoku.SudokuBoard;
import pl.sudoku.SudokuSolver;

public class Main {
    public static void main(String[] args) {
        System.out.println("Projekt - Sudoku.");
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard exampleSudoku = new SudokuBoard(solver);
        exampleSudoku.solveGame();
        System.out.println(exampleSudoku.toString());
        Observer observer = new NonAutomaticBoardChangeObserver(exampleSudoku);
        exampleSudoku.addObserver(observer);
        exampleSudoku.set(0,0,0);
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.info("Przykładowy komunikat.");
    }
}
*/

