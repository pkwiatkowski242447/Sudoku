package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.sudoku.exceptions.IncorrectFieldIndices;
import pl.sudoku.exceptions.InvalidSudokuStructureCoordinatesException;
import pl.sudoku.exceptions.NullObserverException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class SudokuBoardTest {

    private final int[][] correctBoard1 = {
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

    private final int[][] correctBoard2 = {
            {3,6,8,4,1,2,5,7,9},
            {4,5,9,3,6,7,2,8,1},
            {7,2,1,5,8,9,3,4,6},
            {1,8,5,2,4,6,9,3,7},
            {2,7,6,8,9,3,4,1,5},
            {9,3,4,1,7,5,8,6,2},
            {5,1,2,6,3,4,7,9,8},
            {8,4,7,9,2,1,6,5,3},
            {6,9,3,7,5,8,1,2,4}
    };

    private final int[][] incorrectBoard1 = {
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

    private final int[][] incorrectBoard2 = {
            {5,3,9,6,7,8,9,10,2},
            {6,-1,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,1,6,7},
            {8,4,9,7,6,1,4,2,3},
            {5,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,-3,7,4,1,9,6,-2,5},
            {3,4,5,2,8,6,1,7,9}
    };

    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard1);
    private final SudokuSolver exampleSolver_1 = new BacktrackingSudokuSolver();
    private final SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(exampleSolver_1);
    private final SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(incorrectBoard1);
    private final SudokuBoard exampleSudokuBoard_4 = new SudokuBoard(exampleSolver_1);

    @Test
    public void IntroTest() {
        assertNotNull(exampleSolver_1);
        assertEquals(exampleSolver_1.getClass(), BacktrackingSudokuSolver.class);

        assertNotNull(exampleSudokuBoard_1);
        assertNotNull(exampleSudokuBoard_2);
        assertNotNull(exampleSudokuBoard_3);
        assertNotNull(exampleSudokuBoard_4);
    }

    @Test
    public void valueGetterTest() {
        assertThrows(IncorrectFieldIndices.class, () -> exampleSudokuBoard_1.get(-9,-9));
        assertThrows(IncorrectFieldIndices.class, () -> exampleSudokuBoard_1.get(0,-9));
        assertThrows(IncorrectFieldIndices.class, () -> exampleSudokuBoard_1.get(-9,0));
        assertThrows(IncorrectFieldIndices.class, () -> exampleSudokuBoard_1.get(0,9));
        assertThrows(IncorrectFieldIndices.class, () -> exampleSudokuBoard_1.get(9,0));
        assertThrows(IncorrectFieldIndices.class, () -> exampleSudokuBoard_1.get(9,9));
        assertEquals(5, exampleSudokuBoard_1.get(4,4));
        assertEquals(5, exampleSudokuBoard_1.get(0,0));
        assertEquals(2, exampleSudokuBoard_1.get(0,8));
        assertEquals(3, exampleSudokuBoard_1.get(8,0));
        assertEquals(9, exampleSudokuBoard_1.get(8,8));
    }
    @Test
    public void valueSetterTest()
    {
        exampleSudokuBoard_1.set(0,0,9);
        assertEquals(9,exampleSudokuBoard_1.get(0,0));
        exampleSudokuBoard_1.set(0,0,0);
        assertEquals(0,exampleSudokuBoard_1.get(0,0));
        exampleSudokuBoard_1.set(0,0,5);
        assertEquals(5,exampleSudokuBoard_1.get(0,0));
        exampleSudokuBoard_1.set(0,0,-5);
        assertEquals(5,exampleSudokuBoard_1.get(0,0));
        exampleSudokuBoard_1.set(0,0,10);
        assertEquals(5,exampleSudokuBoard_1.get(0,0));
    }

    @Test
    public void sudokuWhenPassedBoardIsCorrect() {
        SudokuBoard testSudokuBoard = new SudokuBoard(correctBoard1);

        assertNotNull(correctBoard1);

        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                assertEquals(testSudokuBoard.get(i, z), correctBoard1[i][z]);
            }
        }
    }

    @Test
    public void sudokuWhenPassedBoardIsIncorrect() {
        SudokuBoard testSudokuBoard = new SudokuBoard(incorrectBoard2);

        assertNotNull(testSudokuBoard);

        boolean areTheSame = true;
        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                if (testSudokuBoard.get(i, z) != incorrectBoard2[i][z]) {
                    areTheSame = false;
                }
            }
        }
        assertFalse(areTheSame);
    }

    @Test
    public void sudokuBoardUniquenessTest() {
        boolean areSudokusIdentical = true;
        exampleSudokuBoard_2.solveGame();
        List<Integer> boardContent = Arrays.asList(new Integer[81]);

        assertNotNull(boardContent);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardContent.set(i * 9 + j, exampleSudokuBoard_2.get(i, j));
                assertNotEquals(null, boardContent.get(i));
            }
        }

        exampleSudokuBoard_2.solveGame();

        for (int i = 0; i < 9; i ++) {
            for (int j = 0; j < 9; j++) {
                if (exampleSudokuBoard_2.get(i, j) != boardContent.get(i * 9 + j)) {
                    areSudokusIdentical = false;
                }
            }
        }
        assertFalse(areSudokusIdentical);
    }

    @Test
    public void checkIfSudokuBoardCorrectTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        exampleSudokuBoard_2.solveGame();
        Method m = exampleSudokuBoard_2.getCheckBoard();
        Boolean result = (Boolean) m.invoke(exampleSudokuBoard_2);
        assertTrue(result);
    }

    @Test
    public void checkIfSudokuBoardIsNotCorrectTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = exampleSudokuBoard_3.getCheckBoard();
        Boolean result = (Boolean) m.invoke(exampleSudokuBoard_3);
        assertFalse(result);
    }

    @Test
    public void addCorrectObserverTest() {
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
        BoardChangeObserver boardChangeObserver = new AutomaticBoardChangeObserver(exampleSudokuBoard_1);
        assertNotNull(boardChangeObserver);
        assertEquals(boardChangeObserver.getClass(), AutomaticBoardChangeObserver.class);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
        BoardChangeObserver boardChangeObserver1 = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        assertNotNull(boardChangeObserver1);
        assertEquals(boardChangeObserver1.getClass(), NonAutomaticBoardChangeObserver.class);
        exampleSudokuBoard_1.addObserver(boardChangeObserver1);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 2);
    }

    @Test
    public void addObserverThatIsNullTest() {
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
        assertThrows(NullObserverException.class, () -> exampleSudokuBoard_1.addObserver(null));
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
    }

    @Test
    public void addObserverAlreadyOnBoardTest() {
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
        BoardChangeObserver boardChangeObserver = new AutomaticBoardChangeObserver(exampleSudokuBoard_1);
        assertNotNull(boardChangeObserver);
        assertEquals(boardChangeObserver.getClass(), AutomaticBoardChangeObserver.class);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
    }

    @Test
    public void removeCorrectObserverTest() {
        BoardChangeObserver boardChangeObserver = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        assertNotNull(boardChangeObserver);
        assertEquals(boardChangeObserver.getClass(), NonAutomaticBoardChangeObserver.class);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
        exampleSudokuBoard_1.removeObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
    }

    @Test
    public void removeObserverThatIsNullTest() {
        BoardChangeObserver boardChangeObserver = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        assertNotNull(boardChangeObserver);
        assertEquals(boardChangeObserver.getClass(), NonAutomaticBoardChangeObserver.class);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
        assertThrows(NullObserverException.class, () -> exampleSudokuBoard_1.removeObserver(null));
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
    }

    @Test
    public void removeObserverThatIsNotOnBoardTest() {
        BoardChangeObserver boardChangeObserver = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        BoardChangeObserver boardChangeObserver1 = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_2);
        assertNotNull(boardChangeObserver);
        assertNotNull(boardChangeObserver1);
        assertEquals(boardChangeObserver.getClass(), NonAutomaticBoardChangeObserver.class);
        assertEquals(boardChangeObserver1.getClass(), NonAutomaticBoardChangeObserver.class);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
        exampleSudokuBoard_1.removeObserver(boardChangeObserver1);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
    }

    @Test
    public void notifyObserversTest() {
        exampleSudokuBoard_1.notifyObservers();
    }

    @Test
    public void getRowTest() {
        exampleSudokuBoard_4.solveGame();
        for (int i = 0; i < 9; i++) {
            assertNotNull(exampleSudokuBoard_4.getRow(i));
        }
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getRow(9));
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getRow(-1));
    }

    @Test
    public void getColumnTest() {
        exampleSudokuBoard_4.solveGame();
        for (int i = 0; i < 9; i++) {
            assertNotNull(exampleSudokuBoard_4.getColumn(i));
        }
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getColumn(9));
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getColumn(-1));
    }

    @Test
    public void getBoxTest() {
        exampleSudokuBoard_4.solveGame();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertNotNull(exampleSudokuBoard_4.getBox(3 * i,3* j));
            }
        }
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getBox(-1,-1));
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getBox(9,9));
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getBox(-1,9));
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getBox(9,-1));
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getBox(9,5));
        assertThrows(InvalidSudokuStructureCoordinatesException.class, () -> exampleSudokuBoard_4.getBox(3,-1));
    }

    @Test
    public void toStringTest() {
        String outputString = exampleSudokuBoard_1.toString();
        assertNotNull(outputString);
        assertTrue(outputString.length() > 0);
    }

    @Test
    public void equalsTestTheSameAsBaseObject() {
        assertTrue(exampleSudokuBoard_1.equals(exampleSudokuBoard_1));
    }

    @Test
    public void equalsTestIsNull() {
        assertFalse(exampleSudokuBoard_1.equals(null));
    }

    @Test
    public void equalsTestIsOfDifferentType() {
        SudokuField someField = new SudokuField();
        assertNotNull(someField);
        assertFalse(exampleSudokuBoard_1.equals(someField));
    }

    @Test
    public void equalsTestIsDifferentFromTheBaseObjectButHasIdenticalFields() {
        SudokuBoard exampleSudokuBoard_5 = new SudokuBoard(correctBoard1);
        assertNotNull(exampleSudokuBoard_5);
        assertTrue(exampleSudokuBoard_1.equals(exampleSudokuBoard_5));
    }

    @Test
    public void hashCodeTest() {
        SudokuBoard exampleSudokuBoard_5 = new SudokuBoard(correctBoard1);
        SudokuBoard exampleSudokuBoard_6 = new SudokuBoard(correctBoard2);
        SudokuBoard exampleSudokuBoard_7 = new SudokuBoard(correctBoard1);

        assertNotNull(exampleSudokuBoard_5);
        assertNotNull(exampleSudokuBoard_6);
        assertNotNull(exampleSudokuBoard_7);

        assertEquals(exampleSudokuBoard_5.hashCode(), exampleSudokuBoard_5.hashCode());
        assertEquals(exampleSudokuBoard_5.hashCode(), exampleSudokuBoard_7.hashCode());
        assertNotEquals(exampleSudokuBoard_5.hashCode(), exampleSudokuBoard_6.hashCode());
    }

    @Test
    public void cloneTest() {
        BoardChangeObserver boardObserver = new AutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(boardObserver);
        SudokuBoard sudokuBoard = exampleSudokuBoard_1.clone();
        assertNotNull(sudokuBoard);
        assertNotSame(sudokuBoard, exampleSudokuBoard_1);
        assertEquals(sudokuBoard, exampleSudokuBoard_1);
        for (Observer observer : sudokuBoard.getSetOfObservers()) {
            for (Observer observer1 : exampleSudokuBoard_1.getSetOfObservers()) {
                assertNotSame(observer, observer1);
            }
        }
        // Test rozłączonści pól SudokuBoard'a

        sudokuBoard.set(0,0,2);
        assertNotEquals(exampleSudokuBoard_1.get(0,0),sudokuBoard.get(0,0));

        assertNotSame(sudokuBoard.getSolver(), exampleSudokuBoard_1.getSolver());
    }
}
