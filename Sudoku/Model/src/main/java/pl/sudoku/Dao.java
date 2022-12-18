package pl.sudoku;

import pl.sudoku.exceptions.InputOutputOperationException;

public interface Dao<T> extends AutoCloseable {
    T read() throws InputOutputOperationException;

    void write(T obj) throws InputOutputOperationException;
}