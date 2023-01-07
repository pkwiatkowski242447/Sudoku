package pl.sudoku.exceptions;

public class SudokuBoardNameDuplicateException extends DataBaseNameException{
    public SudokuBoardNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
