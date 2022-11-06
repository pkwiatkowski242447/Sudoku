package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoxTest {

    int[][] exampleBoard = {
            {5,3,4,6,7,8,9,5,2},
            {6,5,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,1,6,7},
            {8,4,9,7,6,1,4,2,3},
            {5,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };

    public void PositiveTest() {
        SudokuSolver solverek = new BacktrackingSudokuSolver();
        SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(solverek);
        exampleSudokuBoard_1.solveGame();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertTrue(exampleSudokuBoard_1.getBox(3 * i, 3 * j).verify());
            }
        }
    }

    @Test
    public void NegativeTest() {
        SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(exampleBoard);
        assertFalse(exampleSudokuBoard_2.getBox(0,2).verify());
        exampleSudokuBoard_2.set(0,4,0);
        assertFalse(exampleSudokuBoard_2.getBox(0,3).verify());
    }
}
