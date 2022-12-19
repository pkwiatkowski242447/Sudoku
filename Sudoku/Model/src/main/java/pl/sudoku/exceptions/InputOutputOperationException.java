package pl.sudoku.exceptions;

import java.io.IOException;

public class InputOutputOperationException extends GeneralDaoException {
    public InputOutputOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
