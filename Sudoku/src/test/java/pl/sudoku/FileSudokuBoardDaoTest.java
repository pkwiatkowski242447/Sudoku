package pl.sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FileSudokuBoardDaoTest {

    private final SudokuBoardDaoFactory newFactory_1 = new SudokuBoardDaoFactory();
    private final SudokuSolver exampleSolver_1 = new BacktrackingSudokuSolver();
    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(exampleSolver_1);
    private Dao<SudokuBoard> fileSudokuBoardDao;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final PrintStream originalOut = System.out;


    @Test
    public void IntroTest() {
        assertNotNull(newFactory_1);
        assertNotNull(exampleSolver_1);
        assertNotNull(exampleSudokuBoard_1);
    }

    @Test
    public void writeReadTest () {
        fileSudokuBoardDao = newFactory_1.getFileDao("file");
        fileSudokuBoardDao.write(exampleSudokuBoard_1);
        assertNotNull(fileSudokuBoardDao);
        assertTrue(exampleSudokuBoard_1.equals(fileSudokuBoardDao.read()));
    }

    @Test
    public void readException () {
        fileSudokuBoardDao = newFactory_1.getFileDao("yyy");
        assertThrows(RuntimeException.class, () -> {fileSudokuBoardDao.read();});
    }

    @Test
    public void writeException () {
        fileSudokuBoardDao = newFactory_1.getFileDao("???");
        assertThrows(RuntimeException.class, () -> {fileSudokuBoardDao.write(exampleSudokuBoard_1);});
    }

    @Test
    public void fileSudokuBoardDaoCloseTest() {
        FileSudokuBoardDao someDao = (FileSudokuBoardDao) newFactory_1.getFileDao("TestDao");
        System.setOut(new PrintStream(outContent));
        someDao.close();
        System.setOut(originalOut);
        assertEquals(outContent.toString(), "Dokonano zamknięcia zasobów.\r\n");
    }
}
