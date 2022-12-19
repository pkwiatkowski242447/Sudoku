package pl.sudoku.exceptions;

public class SudokuBoardCloneException extends IncorrectValueException {
    public SudokuBoardCloneException(String s, Throwable t) {
        super(s);
        t.printStackTrace();
    }
}
