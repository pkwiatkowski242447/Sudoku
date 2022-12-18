package pl.sudoku;

import pl.sudoku.exceptions.InputOutputOperationException;

public class SudokuBoardDaoFactory {

    public static Dao<SudokuBoard> getFileDao(String fileName)
            throws InputOutputOperationException {
        return new FileSudokuBoardDao(fileName);
    }
}
