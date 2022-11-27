package pl.sudoku;

import org.junit.jupiter.api.Assertions;
import static org.testng.AssertJUnit.assertNotNull;
import org.junit.jupiter.api.Test;

public class SudokuBoardDaoFactoryTest {
    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

    @Test
    public void factoryTest() {
    assertNotNull(factory.getFileDao("pliczek"));
    }
}
