package pl.sudoku;

import pl.sudoku.exceptions.GeneralDaoException;

public class SudokuBoardDaoFactory {

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static Dao<SudokuBoard> getJdbcDao() throws GeneralDaoException {
        return new JdbcSudokuBoardDao();
    }
}
