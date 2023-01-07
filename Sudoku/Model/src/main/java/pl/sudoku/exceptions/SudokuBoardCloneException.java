package pl.sudoku.exceptions;

public class SudokuBoardCloneException extends SudokuBoardException {
    public SudokuBoardCloneException(String s, Throwable t) {
        super(s);
        t.printStackTrace();
    }
}
