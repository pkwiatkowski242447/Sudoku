package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    int[][] exampleBoard_1 = {
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

    /*  Plansza bardzo podobna do exmpleBoard_1, ale w pierwszym wierszu, kolumnie i macierzy 3x3
        zmieniono niektóre wartości, aby plansza, czy też układ liczb był niepoprawny
     */
    int[][] exampleBoard_2 = {
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

    int[][] incorrectBoard = {
            {5,3,4,6,7,8,9,10,2},
            {6,-1,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,1,6,7},
            {8,4,9,7,6,1,4,2,3},
            {5,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,-3,7,4,1,9,6,-2,5},
            {3,4,5,2,8,6,1,7,9}
    };

    SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(exampleBoard_1);
    SudokuSolver exampleSolver_1 = new BacktrackingSudokuSolver();
    SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(exampleSolver_1);
    SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(exampleBoard_2);

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
        SudokuBoard testSudokuBoard = new SudokuBoard(exampleBoard_1);

        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                assertEquals(testSudokuBoard.get(i, z), exampleBoard_1[i][z]);
            }
        }
    }

    @Test
    public void sudokuWhenPassedBoardIsIncorrect() {
        SudokuBoard testSudokuBoard = new SudokuBoard(incorrectBoard);

        boolean areTheSame = true;
        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                if (testSudokuBoard.get(i, z) != exampleBoard_1[i][z]) {
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
        int[][] boardContent = new int[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardContent[i][j] = exampleSudokuBoard_2.get(i, j);
            }
        }

        exampleSudokuBoard_2.solveGame();

        for (int i = 0; i < 9; i ++) {
            for (int j = 0; j < 9; j++) {
                if (exampleSudokuBoard_2.get(i, j) != boardContent[i][j]) {
                    areSudokusIdentical = false;
                }
            }
        }
        assertFalse(areSudokusIdentical);
    }

    @Test
    public void checkIfSudokuBoardCorrectTest() {
        exampleSudokuBoard_2.solveGame();
        assertTrue(exampleSudokuBoard_2.checkBoard());
    }

    @Test
    public void checkIfSudokuBoardIsNotCorrectTest(){
        assertFalse(exampleSudokuBoard_3.checkBoard());
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
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 0);
        Observer observer = new AutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(observer);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 1);
        Observer observer1 = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(observer1);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 2);
    }

    @Test
    public void addObserverThatIsNullTest() {
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 0);
        exampleSudokuBoard_1.addObserver(null);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 0);
    }

    @Test
    public void addObserverAlreadyOnBoardTest() {
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 0);
        Observer observer = new AutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(observer);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 1);
        Observer observer1 = observer;
        exampleSudokuBoard_1.addObserver(observer1);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 1);
    }

    @Test
    public void removeCorrectObserverTest() {
        Observer observer = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(observer);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 1);
        exampleSudokuBoard_1.removeObserver(observer);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 0);
    }

    @Test
    public void removeObserverThatIsNullTest() {
        Observer observer = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(observer);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 1);
        exampleSudokuBoard_1.removeObserver(null);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 1);
    }

    @Test
    public void removeObserverThatIsNotOnBoardTest() {
        Observer observer = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        Observer observer1 = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(observer);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 1);
        exampleSudokuBoard_1.removeObserver(observer1);
        assertEquals(exampleSudokuBoard_1.getObserversList().size(), 1);
    }

    @Test
    public void notifyObserversTest() {
        exampleSudokuBoard_1.notifyObservers();
    }
}
