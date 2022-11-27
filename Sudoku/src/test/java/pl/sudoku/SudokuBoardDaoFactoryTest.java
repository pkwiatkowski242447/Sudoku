package pl.sudoku;

import static org.testng.AssertJUnit.assertNotNull;
import org.junit.jupiter.api.Test;

public class SudokuBoardDaoFactoryTest {
    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

    @Test
    public void factoryTest() {
        assertNotNull(factory.getFileDao("pliczek"));
    }
    @Test
    public void getFileDaoTest() {
        SudokuBoardDaoFactory exampleFactory_1 = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> newDao = exampleFactory_1.getFileDao("TestFile");
        assertNotNull(newDao);
    }
}
