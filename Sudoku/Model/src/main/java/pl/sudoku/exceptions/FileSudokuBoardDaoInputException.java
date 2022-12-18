package pl.sudoku.exceptions;

import java.io.IOException;

public class FileSudokuBoardDaoInputException extends IOException {
    public FileSudokuBoardDaoInputException(String message) {
        super(message);
    }
}
