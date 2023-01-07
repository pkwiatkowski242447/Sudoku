package pl.sudoku.exceptions;

public class DatabaseErrorException extends GeneralDaoException {
    public DatabaseErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
