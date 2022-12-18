package pl.sudoku;

import org.junit.jupiter.api.Test;
import pl.sudoku.exceptions.SudokuBoxInvalidIndexException;
import pl.sudoku.exceptions.SudokuStructureInvalidIndex;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoxTest {

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

    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard);
    private final SudokuBox exampleBox_1 = exampleSudokuBoard_1.getBox(0, 0);

    @Test
    public void IntroTest() {
        assertNotNull(exampleSudokuBoard_1);
        assertNotNull(exampleBox_1);
    }

    @Test
    public void PositiveTest() {
        SudokuBox tempBox;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tempBox = exampleSudokuBoard_1.getBox(3 * i, 3 * j);
                assertNotNull(tempBox);
                assertTrue(tempBox.verify());
            }
        }
    }

    @Test
    public void NegativeTest() {
        SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(incorrectBoard);

        assertNotNull(exampleSudokuBoard_2);

        SudokuBox tempBox = exampleSudokuBoard_2.getBox(0,2);

        assertNotNull(tempBox);

        assertFalse(tempBox.verify());

        exampleSudokuBoard_2.set(0,4,0);

        tempBox = exampleSudokuBoard_2.getBox(0,4);

        assertNotNull(tempBox);

        assertFalse(tempBox.verify());
    }

    @Test
    public void equalsTestWhenTheSameObject() {
        assertTrue(exampleBox_1.equals(exampleBox_1));
    }

    @Test
    public void equalsTestWhenObjectIsNull() {
        assertFalse(exampleBox_1.equals(null));
    }

    @Test
    public void equalsTestWhenObjectIsDifferentType() {
        SudokuField sudokuField_1 = new SudokuField();

        assertNotNull(sudokuField_1);

        assertFalse(exampleBox_1.equals(sudokuField_1));
    }

    @Test
    public void equalsTestWhenFieldsAreTheSame() {
        SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(correctBoard);

        assertNotNull(exampleSudokuBoard_2);

        SudokuBox exampleBox_2 = exampleSudokuBoard_2.getBox(0,0);

        assertNotNull(exampleBox_2);

        assertNotSame(exampleBox_1, exampleBox_2);
        assertTrue(exampleBox_1.equals(exampleBox_2));
    }

    @Test
    public void equalsTestWhenFieldsAreDifferent() {
        SudokuBox exampleBox_2 = exampleSudokuBoard_1.getBox(0,4);

        assertNotNull(exampleBox_1);

        assertNotNull(exampleBox_2);
        assertFalse(exampleBox_1.equals(exampleBox_2));
    }

    @Test
    public void toStringTest() {
        String someString = exampleBox_1.toString();
        assertNotSame(null, someString);
        assertTrue(someString.length() > 0);
    }

    @Test
    public void getValueInStructureTest() {
        assertThrows(SudokuStructureInvalidIndex.class, () -> {exampleBox_1.getValue(-1);});
        assertThrows(SudokuStructureInvalidIndex.class, () -> {exampleBox_1.getValue(10);});
        assertEquals(exampleBox_1.getValue(0), 5);
        assertEquals(exampleBox_1.getValue(8), 8);
    }

    @Test
    public void hashCodeTest() {
        SudokuBox exampleBox_2 = exampleSudokuBoard_1.getBox(0, 4);
        SudokuBox exampleBox_3 = exampleSudokuBoard_1.getBox(2, 2);

        assertNotNull(exampleBox_2);
        assertNotNull(exampleBox_3);

        assertEquals(exampleBox_1.hashCode(), exampleBox_1.hashCode());
        assertEquals(exampleBox_1.hashCode(), exampleBox_3.hashCode());
        assertNotEquals(exampleBox_1.hashCode(), exampleBox_2.hashCode());
    }

    @Test
    public void cloneTest() {
        SudokuBox sudokuBox = exampleBox_1.clone();
        assertNotNull(sudokuBox);
        assertNotSame(sudokuBox, exampleBox_1);
        assertEquals(sudokuBox, exampleBox_1);
        for (int i = 0; i < 9; i++) {
            assertNotSame(sudokuBox.getSudokuFieldList().get(i), exampleBox_1.getSudokuFieldList().get(i));
        }
    }
}
