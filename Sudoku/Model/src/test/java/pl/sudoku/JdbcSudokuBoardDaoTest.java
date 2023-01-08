package pl.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.exceptions.DatabaseErrorException;
import pl.sudoku.exceptions.DatabaseNameException;
import pl.sudoku.exceptions.GeneralDaoException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.testng.Assert.assertThrows;
import static pl.sudoku.SudokuBoardDaoFactory.getJdbcDao;

public class JdbcSudokuBoardDaoTest {

    private final Logger log = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private final SudokuSolver solver_1 = new BacktrackingSudokuSolver();
    private final SudokuBoard exampleSudokuBoard_1 = new SudokuBoard(solver_1);
    private final SudokuBoard exampleSudokuBoard_2 = new SudokuBoard(solver_1);
    private final SudokuBoard exampleSudokuBoard_3 = new SudokuBoard(solver_1);

    @BeforeEach
    public void initializeSudokuBoards() {
        exampleSudokuBoard_1.solveGame();
        exampleSudokuBoard_2.solveGame();
        exampleSudokuBoard_2.solveGame();
    }

    @Test
    public void introTest() {
        assertNotNull(solver_1);
        assertNotNull(exampleSudokuBoard_1);
        assertNotNull(exampleSudokuBoard_2);
        assertNotNull(exampleSudokuBoard_3);
    }

    @Test
    public void writeAndReadToDataBaseTest() throws GeneralDaoException {
        SudokuBoard sudokuFromDataBase = null;
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            someDao.setBoardName("BoardNo1");
            someDao.write(exampleSudokuBoard_1);
            log.debug("Zapis został wykonany.");
            sudokuFromDataBase = someDao.read();
            log.debug("Odczyt został wykonany.");
            someDao.deleteAddedElements("BoardNo1");
            log.debug("Usunięto dodaną planszę.");
        } catch (DatabaseErrorException daoException) {
            log.info("Exception: " + daoException.getClass().toString() + " : " + daoException.getMessage());
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
        assertTrue(sudokuFromDataBase.equals(exampleSudokuBoard_1));
        assertNotSame(sudokuFromDataBase, exampleSudokuBoard_1);
    }

    @Test
    public void writeTestWhenBoardNameIsNull() throws GeneralDaoException {
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            someDao.setBoardName(null);
            assertThrows(DatabaseNameException.class, () -> someDao.write(exampleSudokuBoard_1));
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
    }

    @Test
    public void readTestWhenBoardNameIsNull() throws GeneralDaoException {
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            someDao.setBoardName(null);
            assertThrows(DatabaseNameException.class, () -> someDao.read());
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
    }

    @Test
    public void writeSudokuBoardWithTheSameName() throws GeneralDaoException {
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            someDao.setBoardName("BoardNo1");
            someDao.write(exampleSudokuBoard_1);
            assertThrows(DatabaseNameException.class, () -> someDao.write(exampleSudokuBoard_2));
            someDao.deleteAddedElements("BoardNo1");
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
    }

    @Test
    public void getAndSetBoardNameTest() throws GeneralDaoException {
        List<String> names = new ArrayList<>();
        List<String> getNames = new ArrayList<>();
        String firstName = "BoardNo1";
        String secondName = "BoardNo2";
        String thirdName = "BoardNo3";
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            someDao.setBoardName(firstName);
            someDao.write(exampleSudokuBoard_1);
            getNames.add(someDao.getBoardName());
            someDao.setBoardName(secondName);
            someDao.write(exampleSudokuBoard_2);
            getNames.add(someDao.getBoardName());
            someDao.setBoardName(thirdName);
            someDao.write(exampleSudokuBoard_3);
            getNames.add(someDao.getBoardName());
            names = someDao.retrieveUsedBoardNames();
            someDao.deleteAddedElements(firstName);
            someDao.deleteAddedElements(secondName);
            someDao.deleteAddedElements(thirdName);
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
        assertTrue(names.get(0).equals(firstName));
        assertTrue(getNames.get(0).equals(firstName));
        assertTrue(names.get(1).equals(secondName));
        assertTrue(getNames.get(1).equals(secondName));
        assertTrue(names.get(2).equals(thirdName));
        assertTrue(getNames.get(2).equals(thirdName));
    }

    @Test
    public void readingNonExistentBoard() throws GeneralDaoException {
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            assertThrows(DatabaseNameException.class, () -> someDao.read());
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
    }

    @Test
    public void deleteAddedElementFunctionTest() throws GeneralDaoException {
        List<String> namesAfterRemoval;
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            someDao.setBoardName("BoardNo1");
            someDao.write(exampleSudokuBoard_1);
            someDao.deleteAddedElements("BoardNo1");
            namesAfterRemoval = someDao.retrieveUsedBoardNames();
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
        assertTrue(namesAfterRemoval.isEmpty());
    }

    @Test
    public void deleteAddedElementFunctionTestWhenThereAreMultipleElements() throws GeneralDaoException {
        List<String> namesAfterRemoval;
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            someDao.setBoardName("BoardNo1");
            someDao.write(exampleSudokuBoard_1);
            someDao.setBoardName("BoardNo2");
            someDao.write(exampleSudokuBoard_2);
            someDao.deleteAddedElements("BoardNo2");
            namesAfterRemoval = someDao.retrieveUsedBoardNames();
            someDao.deleteAddedElements("BoardNo1");
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
        assertTrue(namesAfterRemoval.size() == 1);
        assertTrue(namesAfterRemoval.get(0).equals("BoardNo1"));
    }

    @Test
    public void deleteBoardWhichNameIsNull() throws GeneralDaoException {
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            assertThrows(DatabaseNameException.class, () -> someDao.deleteAddedElements(null));
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
    }

    @Test
    public void deleteBoardThatIsNotInADatabase() throws GeneralDaoException {
        try (JdbcSudokuBoardDao someDao = (JdbcSudokuBoardDao) getJdbcDao()) {
            someDao.deleteAddedElements("SomeBoard");
        } catch (DatabaseErrorException daoException) {
            throw new GeneralDaoException(daoException.getMessage(), daoException);
        }
    }
}
