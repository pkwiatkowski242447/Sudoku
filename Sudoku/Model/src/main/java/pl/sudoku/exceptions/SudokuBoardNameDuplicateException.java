package pl.sudoku.exceptions;

public class SudokuBoardNameDuplicateException extends DatabaseNameException {
    public SudokuBoardNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
