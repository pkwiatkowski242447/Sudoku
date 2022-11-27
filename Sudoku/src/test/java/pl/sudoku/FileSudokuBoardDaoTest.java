package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {

    private final SudokuBoardDaoFactory newFactory_1 = new SudokuBoardDaoFactory();
    private final SudokuSolver exampleSolver_1 = new BacktrackingSudokuSolver();
    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(exampleSolver_1);

    @Test
    public void IntroTest() {
        assertNotNull(newFactory_1);
        assertNotNull(exampleSolver_1);
        assertNotNull(exampleSudokuBoard_1);
    }

    @Test
    public void writeAndReadObjectTest() {
        Dao<SudokuBoard> exampleDao = newFactory_1.getFileDao("TestFile");
        assertNotNull(newFactory_1);

        exampleDao.write(exampleSudokuBoard_1);
        SudokuBoard sudokuBoardFromDao = exampleDao.read();

        assertTrue(exampleSudokuBoard_1.equals(sudokuBoardFromDao));
    }

    @Test
    public void readIoExceptionTest() {
        Dao<SudokuBoard> exampleDao_1 = newFactory_1.getFileDao("EmptyFile");
        assertThrows(RuntimeException.class,() -> {
            exampleDao_1.read();
        });
    }

    @Test
    public void writeIoExceptionTest() {
        Dao<SudokuBoard> exampleDao_1 = newFactory_1.getFileDao("?");
        assertThrows(RuntimeException.class, () -> {
            exampleDao_1.write(exampleSudokuBoard_1);
        });
    }

    @Test
    public void readWhenClassNotFoundTest() {
        Dao<SudokuBoard> exampleDao_1 = newFactory_1.getFileDao("TestFile");
        exampleDao_1.write(exampleSudokuBoard_1);
        assertThrows(RuntimeException.class, () -> {
            SudokuSolver newSolver = (SudokuSolver) exampleDao_1.read();
        });
    }
}
