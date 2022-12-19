package pl.sudoku.exceptions;

public class SudokuBoardCloneException extends InvalidIndexException {
    public SudokuBoardCloneException(String s, Throwable t) {
        super(s);
        t.printStackTrace();
    }
}
