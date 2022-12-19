package pl.sudoku.exceptions;

public class SudokuRowInvalidIndexException extends SudokuBoardInvalidIndexException {
    public SudokuRowInvalidIndexException(String message) {
        super(message);
    }
}
