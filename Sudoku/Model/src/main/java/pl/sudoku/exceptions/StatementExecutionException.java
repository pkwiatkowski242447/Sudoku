package pl.sudoku.exceptions;

public class StatementExecutionException extends DatabaseErrorException {
    public StatementExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
