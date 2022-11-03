package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NonAutomaticBoardChangeTest {

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
    int[][] incorrectBoard = {
            {7,3,4,6,7,8,9,1,2},
            {6,7,2,1,9,5,3,4,8},
            {1,9,8,3,4,2,5,6,7},
            {8,5,9,7,6,1,4,2,3},
            {4,2,6,8,5,3,7,9,1},
            {7,1,3,9,2,4,8,5,6},
            {9,6,1,5,3,7,2,8,4},
            {2,8,7,4,1,9,6,3,5},
            {3,4,5,2,8,6,1,7,9}
    };

    SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(correctBoard);
    SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(incorrectBoard);

    @Test
    public void updateIncorrectBoardTest() {
        NonAutomaticBoardChangeObserver observer = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(observer);
        int[][] preChange = observer.getBoard();
        exampleSudokuBoard_1.set(0,2,0);
        int[][] postChange = observer.getBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(preChange[i][j], postChange[i][j]);
            }
        }
    }

    @Test
    public void updateCorrectBoardTest() {
        NonAutomaticBoardChangeObserver observer = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_2);
        exampleSudokuBoard_2.addObserver(observer);
        int[][] preChange = observer.getBoard();
        exampleSudokuBoard_2.set(0,0,5);
        int[][] postChange = observer.getBoard();
        assertTrue(preChange[0][0] != postChange[0][0]);
    }

    @Test
    public void getBoardTest() {
        NonAutomaticBoardChangeObserver observer = new NonAutomaticBoardChangeObserver(exampleSudokuBoard_1);
        exampleSudokuBoard_1.addObserver(observer);

        int[][] observerBoard = observer.getBoard();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(observerBoard[i][j], correctBoard[i][j]);
            }
        }
    }
}
