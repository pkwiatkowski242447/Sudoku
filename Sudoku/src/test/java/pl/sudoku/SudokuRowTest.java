package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuRowTest {

    int[][] incorrectBoard = {
            {5,3,4,6,7,5,9,1,2},
            {6,7,-1,1,9,5,3,4,8},
            {1,9,8,10,4,2,5,6,7},
            {8,5,0,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };

    @Test
    public void positiveVerificationTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(solver);
        exampleSudokuBoard_1.solveGame();
        for (int i =0; i < 9; i++) {
            assertTrue(exampleSudokuBoard_1.getRow(i).verify());
        }
    }

    @Test
    public void negativeVerificationDueToDuplicatesTest() {
        SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(incorrectBoard);
        assertFalse(exampleSudokuBoard_1.getRow(0).verify());
    }

    @Test
    public void negativeVerificationDueToWrongValues() {
        SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(incorrectBoard);
        assertFalse(exampleSudokuBoard_1.getRow(1).verify());
        assertFalse(exampleSudokuBoard_1.getRow(2).verify());
        assertFalse(exampleSudokuBoard_1.getRow(3).verify());
    }
}
