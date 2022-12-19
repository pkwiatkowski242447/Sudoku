package pl.sudoku;

import pl.sudoku.exceptions.FileSudokuBoardDaoInputException;
import pl.sudoku.exceptions.FileSudokuBoardDaoOutputException;
import pl.sudoku.exceptions.InputOutputOperationException;

public interface Dao<T> extends AutoCloseable {
    T read() throws FileSudokuBoardDaoOutputException, FileSudokuBoardDaoInputException;

    void write(T obj) throws FileSudokuBoardDaoInputException, InputOutputOperationException;
}