package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuColumnTest {

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
    private final SudokuColumn exampleColumn_1 = exampleSudokuBoard_1.getColumn(0);

    @Test
    public void IntroTest() {
        assertNotNull(exampleSudokuBoard_1);
        assertNotNull(exampleSudokuBoard_2);
        assertNotNull(exampleColumn_1);
    }

    @Test
    public void positiveVerificationTest() {
        exampleSudokuBoard_1.solveGame();
        SudokuColumn tempColumn;
        for (int i = 0; i < 9; i++) {
            tempColumn = exampleSudokuBoard_1.getColumn(i);
            assertNotNull(tempColumn);
            assertTrue(tempColumn.verify());
        }
    }

    @Test
    public void negativeVerificationDueToDuplicatesTest() {
        SudokuColumn tempColumn = exampleSudokuBoard_2.getColumn(0);

        assertNotNull(tempColumn);

        assertFalse(tempColumn.verify());
        exampleSudokuBoard_2.set(0,0,0);

        tempColumn = exampleSudokuBoard_2.getColumn(0);

        assertNotNull(tempColumn);

        assertFalse(tempColumn.verify());
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(correctBoard);

        assertNotNull(exampleSudokuBoard_3);

        SudokuColumn tempColumn_2 = exampleSudokuBoard_1.getColumn(1);
        SudokuColumn tempColumn_3 = exampleSudokuBoard_3.getColumn(0);

        assertNotNull(tempColumn_2);
        assertNotNull(tempColumn_3);

        assertEquals(exampleColumn_1.hashCode(), exampleColumn_1.hashCode());
        assertNotEquals(exampleColumn_1.hashCode(), tempColumn_2.hashCode());
        assertEquals(exampleColumn_1.hashCode(), tempColumn_3.hashCode());
        assertEquals(exampleColumn_1.hashCode(), tempColumn_3.hashCode());

        SudokuBoard exampleSudokuBoard_4 = new SudokuBoard(correctBoard1);
        assertNotNull(exampleSudokuBoard_4);
        SudokuRow someRow = exampleSudokuBoard_4.getRow(0);
        assertNotNull(someRow);
        assertEquals(exampleColumn_1.hashCode(), someRow.hashCode());
        assertFalse(exampleColumn_1.equals(someRow));
    }

    @Test
    public void equalsTestWhenTheSameObject() {
        assertTrue(exampleColumn_1.equals(exampleColumn_1));
    }

    @Test
    public void equalsTestWhenNullObject() {
        assertFalse(exampleColumn_1.equals(null));
    }

    @Test
    public void equalsTestWhenObjectOfDifferentType() {
        SudokuSolver someSolver = new BacktrackingSudokuSolver();

        assertNotNull(someSolver);
        assertEquals(someSolver.getClass(), BacktrackingSudokuSolver.class);

        assertFalse(exampleColumn_1.equals(someSolver));
    }

    @Test
    public void equalsTestWhenFieldValuesAreTheSame() {
        SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(correctBoard);

        assertNotNull(exampleSudokuBoard_3);

        SudokuColumn exampleColumn_2 = exampleSudokuBoard_3.getColumn(0);

        assertNotNull(exampleColumn_2);

        assertNotSame(exampleColumn_1, exampleColumn_2);
        assertTrue(exampleColumn_1.equals(exampleColumn_2));
    }

    @Test
    public void equalsTestWhenFieldValuesAreDifferent() {
        SudokuColumn exampleColumn_2 = exampleSudokuBoard_1.getColumn(1);

        assertNotNull(exampleColumn_2);

        assertFalse(exampleColumn_1.equals(exampleColumn_2));
    }

    @Test
    public void toStringTest() {
        String someString = exampleColumn_1.toString();
        assertNotNull(someString);
        assertTrue(someString.length() > 0);
    }

    @Test
    public void getValueInStructureTest() {
        assertEquals(exampleColumn_1.getValue(-1), 0);
        assertEquals(exampleColumn_1.getValue(10), 0);
        assertEquals(exampleColumn_1.getValue(0), 5);
        assertEquals(exampleColumn_1.getValue(8), 3);
    }

    @Test
    public void cloneTest() {
        SudokuColumn sudokuColumn = exampleColumn_1.clone();
        assertNotNull(sudokuColumn);
        assertNotSame(sudokuColumn, exampleColumn_1);
        assertEquals(sudokuColumn, exampleColumn_1);
        for (int i = 0; i< 9; i++) {
            assertNotSame(sudokuColumn.getSudokuFieldList().get(i),exampleColumn_1.getSudokuFieldList().get(i));
        }
    }
}
