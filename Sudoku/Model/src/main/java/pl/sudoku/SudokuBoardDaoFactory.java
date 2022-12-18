package pl.sudoku;

import pl.sudoku.exceptions.GeneralDaoException;
import pl.sudoku.exceptions.InputOutputOperationException;

public class SudokuBoardDaoFactory {

    public static Dao<SudokuBoard> getFileDao(String fileName)
            throws GeneralDaoException {
        return new FileSudokuBoardDao(fileName);
    }
}
