package pl.sudoku.exceptions;

public class InvalidIndexException extends IndexOutOfBoundsException {
    public InvalidIndexException(String s) {
        super(s);
    }
}
