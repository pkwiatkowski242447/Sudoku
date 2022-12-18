package pl.sudoku.exceptions;

import java.io.IOException;

public class FileSudokuBoardDaoOutputException extends IOException {
    public FileSudokuBoardDaoOutputException(String message) {
        super(message);
    }
}
