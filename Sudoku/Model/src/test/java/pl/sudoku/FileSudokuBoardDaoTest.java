package pl.sudoku;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import static pl.sudoku.SudokuBoardDaoFactory.getFileDao;

import org.junit.jupiter.api.Test;
import pl.sudoku.exceptions.FileSudokuBoardDaoInputException;
import pl.sudoku.exceptions.FileSudokuBoardDaoOutputException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FileSudokuBoardDaoTest {

    private final SudokuSolver exampleSolver_1 = new BacktrackingSudokuSolver();
    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(exampleSolver_1);
    private Dao<SudokuBoard> fileSudokuBoardDao;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    @Test
    public void IntroTest() {
        assertNotNull(exampleSolver_1);
        assertNotNull(exampleSudokuBoard_1);
    }

    @Test
    public void writeReadTest () throws Exception {
        fileSudokuBoardDao = getFileDao("file");
        fileSudokuBoardDao.write(exampleSudokuBoard_1);
        assertNotNull(fileSudokuBoardDao);
        assertTrue(exampleSudokuBoard_1.equals(fileSudokuBoardDao.read()));
    }

    @Test
    public void readException () {
        fileSudokuBoardDao = getFileDao("yyy");
        assertThrows(FileSudokuBoardDaoOutputException.class, () -> {fileSudokuBoardDao.read();});
    }

    @Test
    public void writeException () {
        fileSudokuBoardDao = getFileDao("???");
        assertThrows(FileSudokuBoardDaoInputException.class, () -> {fileSudokuBoardDao.write(exampleSudokuBoard_1);});
    }

    @Test
    public void fileSudokuBoardDaoCloseTest() {
        FileSudokuBoardDao someDao = (FileSudokuBoardDao) getFileDao("TestDao");
        System.setOut(new PrintStream(outContent));
        someDao.close();
        System.setOut(originalOut);
    }
}
