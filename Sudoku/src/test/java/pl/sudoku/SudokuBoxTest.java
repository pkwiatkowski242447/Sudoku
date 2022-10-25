package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoxTest {

    int[][] incorrectBoard = {
            {5,3,4,6,7,8,9,1,0},
            {6,1,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,11,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,11,8,5,6},
            {9,6,1,5,3,7,2,8,-1},
            {2,8,7,4,1,9,6,-1,5},
            {10,4,5,2,8,6,1,7,9}
    };

    @Test
    public void positiveVerificationTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(solver);
        exampleSudokuBoard_1.solveGame();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertTrue(exampleSudokuBoard_1.getBox(3 * i, 3 * j).verify());
            }
        }
    }

    @Test
    public void negativeVerificationTestDueToDuplicates() {
        SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(incorrectBoard);
        assertFalse(exampleSudokuBoard_1.getBox(0,0).verify());
    }

    @Test
    public void negativeVerificationTestDueToWrongValues() {
        SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(incorrectBoard);
        assertFalse(exampleSudokuBoard_1.getBox(6,6).verify());
        assertFalse(exampleSudokuBoard_1.getBox(6,0).verify());
        assertFalse(exampleSudokuBoard_1.getBox(0,6).verify());
        assertFalse(exampleSudokuBoard_1.getBox(3,3).verify());
    }
}
