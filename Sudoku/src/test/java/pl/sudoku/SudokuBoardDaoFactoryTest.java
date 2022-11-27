package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SudokuBoardDaoFactoryTest {

    @Test
    public void getFileDaoTest() {
        SudokuBoardDaoFactory exampleFactory_1 = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> newDao = exampleFactory_1.getFileDao("TestFile");
        assertNotNull(newDao);
    }
}
