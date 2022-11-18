package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoxTest {

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
            {5,3,9,6,7,8,9,5,2},
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
    public void PositiveTest() {
        SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertTrue(exampleSudokuBoard_1.getBox(3 * i, 3 * j).verify());
            }
        }
    }

    @Test
    public void NegativeTest() {
        SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(incorrectBoard);
        assertFalse(exampleSudokuBoard_2.getBox(0,2).verify());
        exampleSudokuBoard_2.set(0,4,0);
        assertFalse(exampleSudokuBoard_2.getBox(0,3).verify());
    }

    @Test
    public void equalsTest() {
        SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(correctBoard);
        SudokuBox exampleBox_1 = exampleSudokuBoard_3.getBox(0,0);
        SudokuBox exampleBox_2 = exampleSudokuBoard_3.getBox(0, 0);
        assertFalse(exampleBox_1 == null);
        assertTrue(exampleBox_1.equals(exampleBox_2));
        exampleBox_2 = exampleSudokuBoard_3.getBox(4,5);
        assertFalse(exampleBox_1.equals(exampleBox_2));
        assertTrue(exampleBox_2.equals(exampleBox_2));
        assertFalse(exampleBox_1.equals(exampleSudokuBoard_3));
        assertFalse(exampleBox_1.equals(null));
    }

    @Test
    public void toStringTest() {
        SudokuBoard exampleSudokuBoard_4 = new SudokuBoard(correctBoard);
        SudokuBox exampleBox = exampleSudokuBoard_4.getBox(3,3);
        String toString = exampleBox.toString();
        assertTrue(toString != null);
        assertTrue(toString.length() > 0);
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard exampleSudokuBoard_5 = new SudokuBoard(correctBoard);
        SudokuBox exampleBox_4 = exampleSudokuBoard_5.getBox(4,4);
        SudokuBox exampleBox_5 = exampleSudokuBoard_5.getBox(7,7);
        assertTrue(exampleBox_4.hashCode() != 0);
        assertTrue(exampleBox_4.hashCode() != exampleBox_5.hashCode());
        exampleBox_5 = exampleSudokuBoard_5.getBox(4,4);
        assertTrue(exampleBox_4.hashCode() == exampleBox_5.hashCode());
    }
}
