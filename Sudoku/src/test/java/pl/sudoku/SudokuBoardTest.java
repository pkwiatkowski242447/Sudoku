package pl.sudoku;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    Integer[] exampleBoard_1 = {
            5,3,4,6,7,8,9,1,2,
            6,7,2,1,9,5,3,4,8,
            1,9,8,3,4,2,5,6,7,
            8,5,9,7,6,1,4,2,3,
            4,2,6,8,5,3,7,9,1,
            7,1,3,9,2,4,8,5,6,
            9,6,1,5,3,7,2,8,4,
            2,8,7,4,1,9,6,3,5,
            3,4,5,2,8,6,1,7,9
    };

    Integer[] exampleBoard_2 = {
            5,3,4,6,7,8,9,5,2,
            6,5,2,1,9,5,3,4,8,
            1,9,8,3,4,2,1,6,7,
            8,4,9,7,6,1,4,2,3,
            5,2,6,8,5,3,7,9,1,
            7,1,3,9,2,4,8,5,6,
            9,6,1,5,3,7,2,8,4,
            2,8,7,4,1,9,6,3,5,
            3,4,5,2,8,6,1,7,9
    };

    Integer[] incorrectBoard = {
            5,3,4,6,7,8,9,10,2,
            6,-1,2,1,9,5,3,4,8,
            1,9,8,3,4,2,1,6,7,
            8,4,9,7,6,1,4,2,3,
            5,2,6,8,5,3,7,9,1,
            7,1,3,9,2,4,8,5,6,
            9,6,1,5,3,7,2,8,4,
            2,-3,7,4,1,9,6,-2,5,
            3,4,5,2,8,6,1,7,9
    };


    List<Integer> sudokuFieldList_1 = Arrays.asList(exampleBoard_1);
    List<Integer> sudokuFieldList_2 = Arrays.asList(exampleBoard_2);
    List<Integer> sudokuFieldList_3 = Arrays.asList(incorrectBoard);

    SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(sudokuFieldList_1);
    SudokuSolver exampleSolver_1 = new BacktrackingSudokuSolver();
    SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(exampleSolver_1);
    SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(sudokuFieldList_2);
    SudokuBoard exampleSudokuBoard_4 = new SudokuBoard(exampleSolver_1);

    @Test
    public void valueGetterTest() {
        assertEquals(0, exampleSudokuBoard_1.get(-9,-9));
        assertEquals(0, exampleSudokuBoard_1.get(0,-9));
        assertEquals(0, exampleSudokuBoard_1.get(-9,0));
        assertEquals(0, exampleSudokuBoard_1.get(0,9));
        assertEquals(0, exampleSudokuBoard_1.get(9,0));
        assertEquals(0, exampleSudokuBoard_1.get(9,9));
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
        SudokuBoard testSudokuBoard = new SudokuBoard(sudokuFieldList_1);

        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                assertEquals(testSudokuBoard.get(i, z), sudokuFieldList_1.get(i * 9 + z));
            }
        }
    }

    @Test
    public void sudokuWhenPassedBoardIsIncorrect() {
        SudokuBoard testSudokuBoard = new SudokuBoard(sudokuFieldList_3);

        boolean areTheSame = true;
        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                if (testSudokuBoard.get(i, z) != sudokuFieldList_3.get(i * 9 + z)) {
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

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardContent.set(i * 9 + j, exampleSudokuBoard_2.get(i, j));
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
    public void checkIfSudokuIsDisplayedCorrectlyTest() {

        String sudokuOutput_1 = "";
        String sudokuOutput_2 = exampleSudokuBoard_1.toString();

        sudokuOutput_1 += "|-----------------------|\n";
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 3; l++) {
                sudokuOutput_1 += "| ";
                for (int j = 0; j < 3; j++) {
                    for (int z = 0; z < 3; z++) {
                        sudokuOutput_1 += exampleSudokuBoard_1.get(i * 3 + l, j * 3 + z) + " ";
                    }
                    sudokuOutput_1 += "| ";
                }
                sudokuOutput_1 += '\n';
            }
            sudokuOutput_1 += "|-----------------------|\n";
        }

        assertEquals(sudokuOutput_1, sudokuOutput_2);
    }

    @Test
    public void addCorrectObserverTest() {
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
        BoardChangeObserver boardChangeObserver = new AutomaticBoardChangeBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
        BoardChangeObserver boardChangeObserver1 = new NonAutomaticBoardChangeBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(boardChangeObserver1);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 2);
    }

    @Test
    public void addObserverThatIsNullTest() {
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
        exampleSudokuBoard_1.addObserver(null);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
    }

    @Test
    public void addObserverAlreadyOnBoardTest() {
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
        BoardChangeObserver boardChangeObserver = new AutomaticBoardChangeBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
    }

    @Test
    public void removeCorrectObserverTest() {
        BoardChangeObserver boardChangeObserver = new NonAutomaticBoardChangeBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
        exampleSudokuBoard_1.removeObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 0);
    }

    @Test
    public void removeObserverThatIsNullTest() {
        BoardChangeObserver boardChangeObserver = new NonAutomaticBoardChangeBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(boardChangeObserver);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
        exampleSudokuBoard_1.removeObserver(null);
        assertEquals(exampleSudokuBoard_1.getSetOfObservers().size(), 1);
    }

    @Test
    public void removeObserverThatIsNotOnBoardTest() {
        BoardChangeObserver boardChangeObserver = new NonAutomaticBoardChangeBoardChangeObserver(exampleSudokuBoard_1);
        BoardChangeObserver boardChangeObserver1 = new NonAutomaticBoardChangeBoardChangeObserver(exampleSudokuBoard_1);
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
        assertNull(exampleSudokuBoard_4.getRow(9));
        assertNull(exampleSudokuBoard_4.getRow(-1));
    }

    @Test
    public void getColumnTest() {
        exampleSudokuBoard_4.solveGame();
        for (int i = 0; i < 9; i++) {
            assertNotNull(exampleSudokuBoard_4.getColumn(i));
        }
        assertNull(exampleSudokuBoard_4.getColumn(9));
        assertNull(exampleSudokuBoard_4.getColumn(-1));
    }

    @Test
    public void getBoxTest() {
        exampleSudokuBoard_4.solveGame();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertNotNull(exampleSudokuBoard_4.getBox(3 * i,3* j));
            }
        }
        assertNull(exampleSudokuBoard_4.getBox(-1,-1));
        assertNull(exampleSudokuBoard_4.getBox(9,9));
        assertNull(exampleSudokuBoard_4.getBox(-1,9));
        assertNull(exampleSudokuBoard_4.getBox(9,-1));
        assertNull(exampleSudokuBoard_4.getBox(9,5));
        assertNull(exampleSudokuBoard_4.getBox(3,-1));
    }
}
