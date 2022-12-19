package pl.sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AutomaticBoardChangeBoardChangeObserverTest {

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

    private final int[][] incorrectBoard = {
            {9, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };

    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard);
    private final SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(incorrectBoard);
    private final SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(correctBoard);
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final BoardChangeObserver boardChangeObserver_1 = new AutomaticBoardChangeObserver(exampleSudokuBoard_1);
    private final BoardChangeObserver boardChangeObserver_2 =
            new AutomaticBoardChangeObserver(exampleSudokuBoard_2);
    private final BoardChangeObserver boardChangeObserver_3 =
            new AutomaticBoardChangeObserver(exampleSudokuBoard_3);

    @Test
    public void IntroTest() {
        assertNotNull(exampleSudokuBoard_1);
        assertNotNull(exampleSudokuBoard_2);
        assertNotNull(exampleSudokuBoard_3);
        assertNotNull(outContent);
        assertNotNull(boardChangeObserver_1);
        assertNotNull(boardChangeObserver_2);
        assertNotNull(boardChangeObserver_3);
        assertEquals(boardChangeObserver_1.getClass(), AutomaticBoardChangeObserver.class);
        assertEquals(boardChangeObserver_2.getClass(), AutomaticBoardChangeObserver.class);
        assertEquals(boardChangeObserver_3.getClass(), AutomaticBoardChangeObserver.class);
    }

    @Test
    public void updateObserverIncorrectBoardTest() {
        System.setOut(new PrintStream(outContent));
        exampleSudokuBoard_1.addObserver(boardChangeObserver_1);
        exampleSudokuBoard_1.set(0, 0, 0);
        System.setOut(originalOut);
        //assertEquals(outContent.toString(), "Nieprawidłowe uzupełnienie planszy.");
    }

    @Test
    public void updateObserverCorrectBoardTest() {
        exampleSudokuBoard_2.addObserver(boardChangeObserver_2);
        exampleSudokuBoard_2.set(0, 0, 5);

        int[][] board1 = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1[i][j] = exampleSudokuBoard_2.get(i, j);
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(board1[i][j], exampleSudokuBoard_2.get(i, j));
            }
        }
    }

    @Test
    public void hashCodeTest() {
        assertEquals(boardChangeObserver_1.hashCode(), boardChangeObserver_1.hashCode());
        assertNotEquals(boardChangeObserver_1.hashCode(), boardChangeObserver_2.hashCode());
        assertEquals(boardChangeObserver_1.hashCode(), boardChangeObserver_3.hashCode());
    }

    @Test
    public void equalsTestWhenTheSameObject() {
        assertTrue(boardChangeObserver_1.equals(boardChangeObserver_1));
    }

    @Test
    public void equalsTestWhenNullObject() {
        assertFalse(boardChangeObserver_1.equals(null));
    }

    @Test
    public void equalsTestWhenObjectOfDifferentType() {
        SudokuField someField = new SudokuField();

        assertNotNull(someField);

        assertFalse(boardChangeObserver_1.equals(someField));
    }

    @Test
    public void equalsTestWhenObjectOfTheSameType() {
        assertFalse(boardChangeObserver_1.equals(boardChangeObserver_2));
    }

    @Test
    public void toStringTest() {
        String outputString = boardChangeObserver_1.toString();
        assertNotNull(outputString);
        assertTrue(outputString.length() > 0);
    }
}
