package pl.sudoku.exceptions;

public class StatementExecutionException extends DataBaseErrorException{
    public StatementExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
