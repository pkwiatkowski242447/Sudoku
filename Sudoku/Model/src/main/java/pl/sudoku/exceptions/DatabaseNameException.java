package pl.sudoku.exceptions;

public class DatabaseNameException extends DatabaseErrorException {
    public DatabaseNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
