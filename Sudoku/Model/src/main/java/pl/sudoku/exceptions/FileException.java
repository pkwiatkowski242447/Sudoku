package pl.sudoku.exceptions;

public class FileException extends InputOutputOperationException {
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
