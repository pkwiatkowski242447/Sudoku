package pl.sudoku;

import org.junit.jupiter.api.Test;
import pl.sudoku.exceptions.SudokuBoardInvalidValueException;
import pl.sudoku.exceptions.SudokuFieldInvalidIndexException;
import pl.sudoku.exceptions.SudokuStructureInvalidIndex;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {

    private final int[][] correctBoard = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    private final SudokuField exampleSudokuField_1 = new SudokuField();
    private final SudokuField exampleSudokuField_2 = new SudokuField();

    @Test
    public void IntroTest() {
        assertNotSame(null, exampleSudokuField_1);
        assertNotSame(null, exampleSudokuField_2);
    }

    @Test
    public void valueGetterTest() {
        SudokuField[][] table = new SudokuField[9][9];

        assertNotNull(table);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                table[i][j] = new SudokuField();
                assertNotNull(table[i][j]);
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
        assertThrows(SudokuFieldInvalidIndexException.class, () -> {
            exampleSudokuField_1.setFieldValue(-1);
        });
        assertEquals(9, exampleSudokuField_1.getFieldValue());
        assertThrows(SudokuFieldInvalidIndexException.class, () -> {
            exampleSudokuField_1.setFieldValue(10);
        });
        assertEquals(9, exampleSudokuField_1.getFieldValue());
        exampleSudokuField_1.setFieldValue(0);
        assertEquals(0, exampleSudokuField_1.getFieldValue());
    }

    @Test
    public void equalsTestWhenTheSameObject() {
        assertTrue(exampleSudokuField_1.equals(exampleSudokuField_1));
    }

    @Test
    public void equalsTestWhenNullObject() {
        assertFalse(exampleSudokuField_1.equals(null));
    }

    @Test
    public void equalsTestWhenObjectOfDifferentClass() {
        SudokuSolver exampleSolver = new BacktrackingSudokuSolver();

        assertNotNull(exampleSolver);
        assertSame(exampleSolver.getClass(), BacktrackingSudokuSolver.class);

        assertFalse(exampleSudokuField_1.equals(exampleSolver));
    }

    @Test
    public void equalsTestWhenValuesAreTheSame() {
        exampleSudokuField_1.setFieldValue(5);
        exampleSudokuField_2.setFieldValue(5);
        assertTrue(exampleSudokuField_1.equals(exampleSudokuField_2));
    }

    @Test
    public void equalsTestWhenValuesAreDifferent() {
        exampleSudokuField_1.setFieldValue(5);
        exampleSudokuField_2.setFieldValue(3);
        assertFalse(exampleSudokuField_1.equals(exampleSudokuField_2));
    }

    @Test
    public void hashCodeTest() {
        exampleSudokuField_1.setFieldValue(3);
        exampleSudokuField_2.setFieldValue(5);
        SudokuField exampleSudokuField_3 = new SudokuField();
        assertNotNull(exampleSudokuField_3);
        exampleSudokuField_3.setFieldValue(5);
        assertEquals(exampleSudokuField_1.hashCode(), exampleSudokuField_1.hashCode());
        assertNotEquals(exampleSudokuField_1.hashCode(), exampleSudokuField_2.hashCode());
        assertEquals(exampleSudokuField_2.hashCode(), exampleSudokuField_3.hashCode());
    }

    @Test
    public void toStringTest() {
        exampleSudokuField_1.setFieldValue(3);
        exampleSudokuField_2.setFieldValue(3);
        SudokuField exampleSudokuField_3 = new SudokuField();
        assertNotNull(exampleSudokuField_3);
        exampleSudokuField_3.setFieldValue(5);
        assertEquals(exampleSudokuField_2.toString(), exampleSudokuField_2.toString());
        assertNotEquals(exampleSudokuField_1.toString(), exampleSudokuField_3.toString());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        exampleSudokuField_1.setFieldValue(9);
        SudokuField sudokuField = exampleSudokuField_1.clone();
        assertNotNull(sudokuField);
        assertTrue(sudokuField.equals(exampleSudokuField_1));
        assertNotSame(sudokuField, exampleSudokuField_1);
        // Test rozłączonści
        sudokuField.setFieldValue(2);
        assertNotEquals(exampleSudokuField_1.getFieldValue(), sudokuField.getFieldValue());
    }

    @Test
    public void compareToTest() {
        exampleSudokuField_1.setFieldValue(1);
        exampleSudokuField_2.setFieldValue(2);
        assertTrue(exampleSudokuField_1.compareTo(exampleSudokuField_2) < 0);
        exampleSudokuField_2.setFieldValue(1);
        assertTrue(exampleSudokuField_1.compareTo(exampleSudokuField_2) == 0);
        exampleSudokuField_1.setFieldValue(3);
        assertTrue(exampleSudokuField_1.compareTo(exampleSudokuField_2) > 0);
        assertThrows(NullPointerException.class, () -> {
            exampleSudokuField_1.compareTo(null);
        });
    }

}
