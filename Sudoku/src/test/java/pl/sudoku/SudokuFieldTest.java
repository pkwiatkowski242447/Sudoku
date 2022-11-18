package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {

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
    SudokuField exampleSudokuField_1 = new SudokuField();
    SudokuField exampleSudokuField_2 = new SudokuField();

    @Test
    public void valueGetterTest() {
        SudokuField[][] table = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                table[i][j] = new SudokuField();
                table[i][j].setFieldValue(correctBoard[i][j]);
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(correctBoard[i][j], table[i][j].getFieldValue());
            }
        }
        assertEquals(0, exampleSudokuField_1.getFieldValue());
    }
    @Test
    public void valueSetterTest() {
        exampleSudokuField_1.setFieldValue(5);
        assertEquals(5, exampleSudokuField_1.getFieldValue());
        exampleSudokuField_1.setFieldValue(9);
        assertEquals(9, exampleSudokuField_1.getFieldValue());
        exampleSudokuField_1.setFieldValue(-1);
        assertEquals(9, exampleSudokuField_1.getFieldValue());
        exampleSudokuField_1.setFieldValue(10);
        assertEquals(9, exampleSudokuField_1.getFieldValue());
        exampleSudokuField_1.setFieldValue(0);
        assertEquals(0, exampleSudokuField_1.getFieldValue());
    }

    @Test
    public void equalsTest() {
       SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard);
       exampleSudokuField_1.setFieldValue(1);
       exampleSudokuField_2.setFieldValue(1);
       assertTrue(exampleSudokuField_1.equals(exampleSudokuField_2));
       exampleSudokuField_2.setFieldValue(2);
       assertFalse(exampleSudokuField_1.equals(exampleSudokuField_2));
       assertFalse(exampleSudokuField_2.equals(null));
       assertTrue(exampleSudokuField_2.equals(exampleSudokuField_2));
       assertFalse(exampleSudokuField_1.equals(exampleSudokuBoard_1));
       assertFalse(exampleSudokuField_1.equals(null));
    }

    @Test
    public void toStringTest() {
        exampleSudokuField_1.setFieldValue(4);
        String toString = exampleSudokuField_1.toString();
        assertTrue(toString.length() > 0);
        assertFalse(toString == null);

    }

    @Test
    public void hashCodeTest() {
        exampleSudokuField_1.setFieldValue(5);
        assertTrue(exampleSudokuField_1.hashCode() != 0);
        exampleSudokuField_2.setFieldValue(5);
        assertTrue(exampleSudokuField_1.hashCode() == exampleSudokuField_2.hashCode());
    }

}
