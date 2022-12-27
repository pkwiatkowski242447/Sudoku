package pl.sudoku.exceptions;

public class SudokuFieldInvalidValueException extends SudokuBoardInvalidValueException {
    public SudokuFieldInvalidValueException(String s) {
        super(s);
    }
}
