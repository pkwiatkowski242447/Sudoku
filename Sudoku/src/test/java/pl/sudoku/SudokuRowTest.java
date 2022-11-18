package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuRowTest {

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
    @Test
    public void positiveVerificationTest() {
        SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard);
        exampleSudokuBoard_1.solveGame();
        for (int i =0; i < 9; i++) {
            assertTrue(exampleSudokuBoard_1.getRow(i).verify());
        }
    }

    @Test
    public void negativeVerificationDueToDuplicatesTest() {
        SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(incorrectBoard);
        assertFalse(exampleSudokuBoard_2.getRow(0).verify());
        exampleSudokuBoard_2.set(0,7,1);
        assertTrue(exampleSudokuBoard_2.getRow(0).verify());
    }

    @Test
    public void equalsTest() {
        SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(correctBoard);
        SudokuRow exampleRow_1 = exampleSudokuBoard_3.getRow(0);
        SudokuRow exampleRow_2 = exampleSudokuBoard_3.getRow(0);
        assertFalse(exampleRow_1 == null);
        assertTrue(exampleRow_1.equals(exampleRow_2));
        exampleRow_2 = exampleSudokuBoard_3.getRow(1);
        assertFalse(exampleRow_1.equals(exampleRow_2));
        assertTrue(exampleRow_2.equals(exampleRow_2));
        assertFalse(exampleRow_1.equals(exampleSudokuBoard_3));
        assertFalse(exampleRow_1.equals(null));
    }

    @Test
    public void toStringTest() {
        SudokuBoard exampleSudokuBoard_4 = new SudokuBoard(correctBoard);
        SudokuRow exampleRow_3 = exampleSudokuBoard_4.getRow(3);
        String toString = exampleRow_3.toString();
        assertTrue(toString != null);
        assertTrue(toString.length() > 0);
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard exampleSudokuBoard_5 = new SudokuBoard(correctBoard);
        SudokuRow exampleRow_4 = exampleSudokuBoard_5.getRow(4);
        SudokuRow exampleRow_5 = exampleSudokuBoard_5.getRow(5);
        assertTrue(exampleRow_4.hashCode() != 0);
        assertTrue(exampleRow_4.hashCode() != exampleRow_5.hashCode());
        exampleRow_5 = exampleSudokuBoard_5.getRow(4);
        assertTrue(exampleRow_4.hashCode() == exampleRow_5.hashCode());
    }
}
