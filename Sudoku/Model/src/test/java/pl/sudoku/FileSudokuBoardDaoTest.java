package pl.sudoku;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import static pl.sudoku.SudokuBoardDaoFactory.getFileDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.exceptions.FileSudokuBoardDaoInputException;
import pl.sudoku.exceptions.FileSudokuBoardDaoOutputException;

import pl.sudoku.exceptions.GeneralDaoException;
import pl.sudoku.exceptions.InputOutputOperationException;

public class FileSudokuBoardDaoTest {

    private final SudokuSolver exampleSolver_1 = new BacktrackingSudokuSolver();
    private final SudokuSolver exampleSolver_2 = new BacktrackingSudokuSolver();
    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(exampleSolver_1);
    private final SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(exampleSolver_2);
    private final Logger log = LoggerFactory.getLogger(FileSudokuBoardDaoTest.class);

    @BeforeEach
    public void prepareBoards() {
        exampleSudokuBoard_1.solveGame();
        exampleSudokuBoard_2.solveGame();
    }

    @Test
    public void IntroTest() {
        assertNotNull(exampleSolver_1);
        assertNotNull(exampleSudokuBoard_1);
        assertNotNull(exampleSudokuBoard_2);
    }

    @Test
    public void writeReadTest() throws Exception {
        SudokuBoard sudokuFromFile;
        try (Dao<SudokuBoard> fileSudokuBoardDao = getFileDao("someFileName")) {
            fileSudokuBoardDao.write(exampleSudokuBoard_1);
            sudokuFromFile = fileSudokuBoardDao.read();
        } catch (InputOutputOperationException e) {
            throw new GeneralDaoException(e.getMessage(), e.getCause());
        }
        assertTrue(exampleSudokuBoard_1.equals(sudokuFromFile));
        log.debug("Test 1.: " + sudokuFromFile);
    }

    @Test
    public void writeReadTestWithClosingDao() throws Exception {
        SudokuBoard sudokuFromFile;
        try (Dao<SudokuBoard> fileSudokuBoardDao = getFileDao("someFileName")) {
            fileSudokuBoardDao.write(exampleSudokuBoard_1);
        } catch (InputOutputOperationException e) {
            throw new GeneralDaoException(e.getMessage(), e.getCause());
        }

        try (Dao<SudokuBoard> fileSudokuBoardDao = getFileDao("someFileName")) {
            sudokuFromFile = fileSudokuBoardDao.read();
        } catch (InputOutputOperationException e) {
            throw new GeneralDaoException(e.getMessage(), e.getCause());
        }

        assertTrue(exampleSudokuBoard_1.equals(sudokuFromFile));
        log.debug("Test 1.: " + sudokuFromFile);
    }

    @Test
    public void writeReadTestMultiple() throws Exception {
        SudokuBoard sudoku1;
        SudokuBoard sudoku2;
        try (Dao<SudokuBoard> fileSudokuBoardDao = getFileDao("someFileName")) {
            fileSudokuBoardDao.write(exampleSudokuBoard_1);
            fileSudokuBoardDao.write(exampleSudokuBoard_2);
            sudoku1 = fileSudokuBoardDao.read();
            sudoku2 = fileSudokuBoardDao.read();
        } catch (InputOutputOperationException e) {
            throw new GeneralDaoException(e.getMessage(), e.getCause());
        }
        log.debug("Test 1.: " + sudoku1.toString());
        log.debug("Test 2.: " + sudoku2.toString());
        assertTrue(exampleSudokuBoard_1.equals(sudoku1));
        assertTrue(exampleSudokuBoard_2.equals(sudoku2));
    }

    @Test
    public void writeAndReadTwiceWithClosingResources() throws Exception {
        SudokuBoard sudokuBoard1;
        SudokuBoard sudokuBoard2;
        try (Dao<SudokuBoard> writeDao = getFileDao("someName")) {
            writeDao.write(exampleSudokuBoard_1);
            writeDao.write(exampleSudokuBoard_2);
        } catch (InputOutputOperationException exception) {
            throw new GeneralDaoException(exception.getMessage(), exception.getCause());
        }

        try(Dao<SudokuBoard> readDao = getFileDao("someName")) {
            sudokuBoard1 = readDao.read();
            sudokuBoard2 = readDao.read();
        } catch (InputOutputOperationException exception) {
            throw new GeneralDaoException(exception.getMessage(), exception.getCause());
        }
        log.debug("Test 1.: " + sudokuBoard1.toString());
        log.debug("Test 2.: " + sudokuBoard2.toString());
        assertTrue(sudokuBoard1.equals(exampleSudokuBoard_1));
        assertTrue(sudokuBoard2.equals(exampleSudokuBoard_2));
    }

    @Test
    public void readException() throws Exception {
        try (Dao<SudokuBoard> fileSudokuBoardDao = getFileDao("yyy")) {
            assertThrows(FileSudokuBoardDaoInputException.class, () -> fileSudokuBoardDao.read());
        } catch (InputOutputOperationException e) {
            throw new GeneralDaoException(e.getMessage(), e.getCause());
        }
    }

    @Test
    public void fileExceptionTest() throws Exception {
        Dao<SudokuBoard> someDao = getFileDao("???");
        assertThrows(FileSudokuBoardDaoInputException.class, () -> someDao.read());
        someDao.close();
    }

    @Test
    public void readTwiceTheSameObjectTest() throws Exception {
        SudokuBoard newSudokuBoard = exampleSudokuBoard_1;
        assertNotNull(newSudokuBoard);
        SudokuBoard readBoard1;
        SudokuBoard readBoard2;

        assertSame(exampleSudokuBoard_1, newSudokuBoard);
        try (Dao<SudokuBoard> someDao = getFileDao("correctDao")) {
            someDao.write(exampleSudokuBoard_1);
            someDao.write(newSudokuBoard);
            readBoard1 = someDao.read();
            readBoard2 = someDao.read();
        } catch (InputOutputOperationException exception) {
            throw new GeneralDaoException(exception.getMessage(), exception.getCause());
        }
        assertTrue(readBoard1.equals(readBoard2));
    }

    @Test
    public void writeExceptionTest() throws Exception {
        Dao<SudokuBoard> fileSudokuBoardDao = getFileDao("???");
        assertThrows(FileSudokuBoardDaoOutputException.class, () -> fileSudokuBoardDao.write(exampleSudokuBoard_1));
        fileSudokuBoardDao.close();
    }
}
