package pl.sudoku.exceptions;

import java.io.IOException;

public class GeneralDaoException extends IOException {
    public GeneralDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}