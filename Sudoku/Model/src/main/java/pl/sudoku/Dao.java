package pl.sudoku;

import pl.sudoku.exceptions.DataBaseNameException;
import pl.sudoku.exceptions.FileSudokuBoardDaoInputException;
import pl.sudoku.exceptions.FileSudokuBoardDaoOutputException;
import pl.sudoku.exceptions.StatementExecutionException;

public interface Dao<T> extends AutoCloseable {
    T read() throws FileSudokuBoardDaoOutputException, FileSudokuBoardDaoInputException, DataBaseNameException, StatementExecutionException;

    void write(T obj) throws FileSudokuBoardDaoOutputException, DataBaseNameException, StatementExecutionException;
}