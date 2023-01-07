package pl.sudoku.exceptions;

public class CloseDatabaseException extends DatabaseErrorException {
    public CloseDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
