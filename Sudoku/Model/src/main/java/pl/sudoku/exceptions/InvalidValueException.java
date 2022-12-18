package pl.sudoku.exceptions;

public class InvalidValueException extends IndexOutOfBoundsException {
    public InvalidValueException(String s) {
        super(s);
    }
}
