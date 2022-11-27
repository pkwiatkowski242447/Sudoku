package pl.sudoku;

import org.junit.jupiter.api.Assertions;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import org.junit.jupiter.api.Test;

public class FileSudokuBoardDaoTest {

    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private SudokuSolver solverek = new BacktrackingSudokuSolver();
    private SudokuBoard board = new SudokuBoard(solverek);
    private Dao<SudokuBoard> fileSudokuBoardDao;

    @Test
    public void writeReadTest () {
        fileSudokuBoardDao = factory.getFileDao("file");
        fileSudokuBoardDao.write(board);
        SudokuBoard board_2 = fileSudokuBoardDao.read();
        assertNotNull(fileSudokuBoardDao);
        assertTrue(board.equals(fileSudokuBoardDao.read()));
    }

    @Test
    public void readException () {
        fileSudokuBoardDao = factory.getFileDao("yyy");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {fileSudokuBoardDao.read();});
    }

    @Test
    public void writeException () {
        fileSudokuBoardDao = factory.getFileDao("???");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {fileSudokuBoardDao.write(board);});
    }

}
