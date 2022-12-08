package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuRowTest {

    private final int[][] correctBoard = {
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
    private final int[][] incorrectBoard = {
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

    private final int[][] correctBoard1 = {
            {5,6,1,8,4,7,9,2,3},
            {3,7,9,5,2,1,6,8,4},
            {4,2,8,9,6,3,1,7,5},
            {6,1,3,7,8,9,5,4,2},
            {7,9,4,6,5,2,3,1,8},
            {8,5,2,1,3,4,7,9,6},
            {9,3,5,4,7,8,2,6,1},
            {1,4,6,2,9,5,8,3,7},
            {2,8,7,3,1,6,4,5,9}
    };

    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard);
    private final SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(incorrectBoard);
    private final SudokuRow exampleRow_1 = exampleSudokuBoard_1.getRow(0);
    @Test
    public void IntroTest() {
        assertNotNull(exampleSudokuBoard_1);
        assertNotNull(exampleSudokuBoard_2);
        assertNotNull(exampleRow_1);
    }

    @Test
    public void positiveVerificationTest() {
        exampleSudokuBoard_1.solveGame();
        SudokuRow tempRow;
        for (int i =0; i < 9; i++) {
            tempRow = exampleSudokuBoard_1.getRow(i);
            assertNotNull(tempRow);
            assertEquals(tempRow.getClass(), SudokuRow.class);
            assertTrue(tempRow.verify());
        }
    }

    @Test
    public void negativeVerificationDueToDuplicatesTest() {
        SudokuRow tempRow = exampleSudokuBoard_2.getRow(0);

        assertNotNull(tempRow);

        assertFalse(exampleSudokuBoard_2.getRow(0).verify());
        exampleSudokuBoard_2.set(0,7,1);

        tempRow = exampleSudokuBoard_2.getRow(0);

        assertNotNull(tempRow);

        assertTrue(exampleSudokuBoard_2.getRow(0).verify());
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(correctBoard);

        assertNotNull(exampleSudokuBoard_3);

        SudokuRow exampleRow_2 = exampleSudokuBoard_1.getRow(1);
        SudokuRow exampleRow_3 = exampleSudokuBoard_3.getRow(0);

        assertNotNull(exampleRow_2);
        assertNotNull(exampleRow_3);

        assertEquals(exampleRow_1.hashCode(), exampleRow_1.hashCode());
        assertNotEquals(exampleRow_1.hashCode(), exampleRow_2.hashCode());
        assertEquals(exampleRow_1.hashCode(), exampleRow_3.hashCode());

        //Dopisane

        SudokuBoard exampleSudokuBoard_4 = new SudokuBoard(correctBoard1);
        assertNotNull(exampleSudokuBoard_4);
        SudokuColumn someColumn = exampleSudokuBoard_4.getColumn(0);
        assertNotNull(someColumn);
        assertEquals(exampleRow_1.hashCode(), someColumn.hashCode());
        assertFalse(exampleRow_1.equals(someColumn));
    }

    @Test
    public void equalsWhenTheSameObject() {
        assertTrue(exampleRow_1.equals(exampleRow_1));
    }

    @Test
    public void equalsWhenNullObject() {
        assertFalse(exampleRow_1.equals(null));
    }

    @Test
    public void equalsWhenObjectOfDifferentType() {
        SudokuSolver someSolver = new BacktrackingSudokuSolver();

        assertNotNull(someSolver);
        assertEquals(someSolver.getClass(), BacktrackingSudokuSolver.class);

        assertFalse(exampleRow_1.equals(someSolver));
    }

    @Test
    public void equalsWhenFieldsAreTheSame() {
        SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(correctBoard);

        assertNotNull(exampleSudokuBoard_3);

        SudokuRow exampleRow_2 = exampleSudokuBoard_3.getRow(0);

        assertNotSame(exampleRow_1, exampleRow_2);
        assertTrue(exampleRow_1.equals(exampleRow_2));
    }

    @Test
    public void equalsWhenFieldAreDifferent() {
        SudokuRow exampleRow_2 = exampleSudokuBoard_1.getRow(1);

        assertNotNull(exampleRow_2);

        assertNotEquals(exampleRow_1, exampleRow_2);
        assertFalse(exampleRow_1.equals(exampleRow_2));
    }

    @Test
    public void toStringTest() {
        String outputString = exampleRow_1.toString();
        assertNotNull(outputString);
        assertTrue(outputString.length() > 0);
    }

    @Test
    public void getValueInStructureTest() {
        assertEquals(exampleRow_1.getValue(-1), 0);
        assertEquals(exampleRow_1.getValue(10), 0);
        assertEquals(exampleRow_1.getValue(0), 5);
        assertEquals(exampleRow_1.getValue(8), 2);
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        SudokuRow sudokuRow = exampleRow_1.clone();
        assertNotNull(sudokuRow);
        assertNotSame(sudokuRow, exampleRow_1);
        assertTrue(sudokuRow.equals(exampleRow_1));
        for (int i = 0; i < 9; i++) {
            assertNotSame(exampleRow_1.getSudokuFieldList().get(i), sudokuRow.getSudokuFieldList().get(i));
        }
    }
}
