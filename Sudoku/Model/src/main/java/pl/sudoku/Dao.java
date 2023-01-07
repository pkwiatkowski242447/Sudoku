package pl.sudoku;

import pl.sudoku.exceptions.GeneralDaoException;


public interface Dao<T> extends AutoCloseable {
    T read() throws GeneralDaoException;

    void write(T obj) throws GeneralDaoException;
}