package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NonAutomaticBoardChangeTest {

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
            {9,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };

    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard);
    private final SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(incorrectBoard);
    private final SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(correctBoard);
    private final BoardChangeObserver observer_1 = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
    private final BoardChangeObserver observer_2 = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_2);
    private final BoardChangeObserver observer_3 = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_3);

    @Test
    public void IntroTest() {
        assertNotNull(exampleSudokuBoard_1);
        assertNotNull(exampleSudokuBoard_2);
        assertNotNull(exampleSudokuBoard_3);
        assertNotNull(observer_1);
        assertNotNull(observer_2);
        assertNotNull(observer_3);
        assertEquals(observer_1.getClass(), NonAutomaticBoardChangeObserver.class);
        assertEquals(observer_2.getClass(), NonAutomaticBoardChangeObserver.class);
        assertEquals(observer_3.getClass(), NonAutomaticBoardChangeObserver.class);
    }

    @Test
    public void updateIncorrectBoardTest() {
        exampleSudokuBoard_1.addObserver(observer_1);
        int[][] preChange = observer_1.getBoard();
        exampleSudokuBoard_1.set(0,2,0);
        int[][] postChange = observer_1.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(preChange[i][j], postChange[i][j]);
            }
        }
    }

    @Test
    public void updateCorrectBoardTest() {
        exampleSudokuBoard_2.addObserver(observer_2);
        int[][] preChange = observer_2.getBoard();
        exampleSudokuBoard_2.set(0,0,5);
        int[][] postChange = observer_2.getBoard();
        assertTrue(preChange[0][0] != postChange[0][0]);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(observer_1.hashCode(), observer_1.hashCode());
        assertNotEquals(observer_1.hashCode(), observer_2.hashCode());
        assertEquals(observer_1.hashCode(), observer_3.hashCode());
    }

    @Test
    public void equalsTestWhenTheSameObject() {
        assertTrue(observer_1.equals(observer_1));
    }

    @Test
    public void equalsTestWhenNullObject() {
        assertFalse(observer_1.equals(null));
    }

    @Test
    public void equalsTestWhenObjectOfDifferentType() {
        SudokuField someField = new SudokuField();

        assertNotNull(someField);

        assertFalse(observer_1.equals(someField));
    }

    @Test
    public void equalsTestWhenObjectOfTheSameType() {
        assertFalse(observer_1.equals(observer_2));
    }

    @Test
    public void toStringTest() {
        String outputString = observer_1.toString();
        assertNotNull(outputString);
        assertTrue(outputString.length() > 0);
    }
}
