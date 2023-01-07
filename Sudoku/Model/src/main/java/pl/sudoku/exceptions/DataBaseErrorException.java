package pl.sudoku.exceptions;

public class DataBaseErrorException extends GeneralDaoException{
    public DataBaseErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
