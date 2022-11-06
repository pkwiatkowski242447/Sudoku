package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AutomaticBoardChangeObserverTest {

    int[][] correctBoard = {
            {5,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };
    int[][] incorrectBoard = {
            {7,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };
    SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard);
    SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(incorrectBoard);
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;

    @Test
    public void updateObserverIncorrectBoardTest() {
        System.setOut(new PrintStream(outContent));
        Observer observer = new AutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(observer);
        exampleSudokuBoard_1.set(0,0,0);
        System.setOut(originalOut);
        assertEquals(outContent.toString(), "Nieprawidłowe uzupełnienie planszy.");
    }

    @Test
    public void updateObserverCorrectBoardTest() {
        AutomaticBoardChangeObserver observer = new AutomaticBoardChangeObserver(exampleSudokuBoard_2);
        exampleSudokuBoard_2.addObserver(observer);
        exampleSudokuBoard_2.set(0,0,5);

        int[][] board1 = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1[i][j] = exampleSudokuBoard_2.get(i,j);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(board1[i][j], exampleSudokuBoard_2.get(i, j));
            }
        }
    }
}
