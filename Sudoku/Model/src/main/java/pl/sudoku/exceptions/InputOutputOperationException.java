package pl.sudoku.exceptions;

import java.io.IOException;

public class InputOutputOperationException extends IOException {
    public InputOutputOperationException(String message) {
        super(message);
    }
}
