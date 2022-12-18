package pl.sudoku.exceptions;

public class IncorrectCoordinatesException extends IndexOutOfBoundsException {
    public IncorrectCoordinatesException(String s) {
        super(s);
    }
}
