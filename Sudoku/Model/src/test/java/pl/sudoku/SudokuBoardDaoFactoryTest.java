package pl.sudoku;

import static org.testng.AssertJUnit.assertNotNull;
import static pl.sudoku.SudokuBoardDaoFactory.getFileDao;
import org.junit.jupiter.api.Test;

public class SudokuBoardDaoFactoryTest {

    @Test
    public void sudokuBoardDaoFactoryTest() {
        SudokuBoardDaoFactory newFactory_1 = new SudokuBoardDaoFactory();
        assertNotNull(newFactory_1);
    }

    @Test
    public void getFileDaoTest() {
        Dao<SudokuBoard> newDao = getFileDao("TestFile");
        assertNotNull(newDao);
    }
}
