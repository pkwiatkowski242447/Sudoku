package pl.sudoku;

import static org.testng.AssertJUnit.assertNotNull;
import static pl.sudoku.SudokuBoardDaoFactory.getFileDao;
import org.junit.jupiter.api.Test;
import pl.sudoku.exceptions.InputOutputOperationException;

import java.io.IOException;

public class SudokuBoardDaoFactoryTest {

    @Test
    public void sudokuBoardDaoFactoryTest() {
        SudokuBoardDaoFactory newFactory_1 = new SudokuBoardDaoFactory();
        assertNotNull(newFactory_1);
    }

    @Test
    public void getFileDaoTest() throws InputOutputOperationException {
        Dao<SudokuBoard> newDao = getFileDao("TestFile");
        assertNotNull(newDao);
    }
}
