package pl.sudoku;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;
import static pl.sudoku.SudokuBoardDaoFactory.getFileDao;
import org.junit.jupiter.api.Test;
import pl.sudoku.exceptions.FileException;
import pl.sudoku.exceptions.GeneralDaoException;
import pl.sudoku.exceptions.InputOutputOperationException;

public class FileSudokuBoardDaoTest {

    private final SudokuSolver exampleSolver_1 = new BacktrackingSudokuSolver();
    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(exampleSolver_1);

    @Test
    public void IntroTest() {
        assertNotNull(exampleSolver_1);
        assertNotNull(exampleSudokuBoard_1);
    }

    @Test
    public void writeReadTest () throws Exception {
        SudokuBoard sudokuFromFile;
        try (Dao<SudokuBoard> fileSudokuBoardDao = getFileDao("someFileName")) {
            fileSudokuBoardDao.write(exampleSudokuBoard_1);
            sudokuFromFile = fileSudokuBoardDao.read();
        } catch (InputOutputOperationException e) {
            throw new GeneralDaoException(e.getMessage(), e.getCause());
        }
        assertTrue(exampleSudokuBoard_1.equals(sudokuFromFile));
    }

    @Test
    public void readException () throws Exception {
        try(Dao<SudokuBoard> fileSudokuBoardDao = getFileDao("yyy")) {
            assertThrows(InputOutputOperationException.class, () -> fileSudokuBoardDao.read());
        } catch (InputOutputOperationException e) {
            throw new GeneralDaoException(e.getMessage(), e.getCause());
        }
    }

    @Test
    public void fileExceptionTest () {
        assertThrows(FileException.class, () -> getFileDao("???"));
    }

    @Test
    public void writeExceptionTest() throws Exception {
        Dao<SudokuBoard> fileSudokuBoardDao = getFileDao("correctName");
        fileSudokuBoardDao.close();
        assertThrows(InputOutputOperationException.class, () -> fileSudokuBoardDao.write(exampleSudokuBoard_1));
        assertThrows(InputOutputOperationException.class, () -> fileSudokuBoardDao.close());
    }
}
