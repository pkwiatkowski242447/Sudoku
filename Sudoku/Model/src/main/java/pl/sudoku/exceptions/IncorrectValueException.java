package pl.sudoku.exceptions;

public class IncorrectValueException extends IllegalArgumentException {
    public IncorrectValueException(String s) {
        super(s);
    }
}
