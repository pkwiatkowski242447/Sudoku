package org.example.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pl.sudoku.SudokuBoard;

public class SudokuFieldWrapper {
    private IntegerProperty fieldValue = new SimpleIntegerProperty();
    private final SudokuBoard sudokuBoard;
    private final int xValue;
    private final int yValue;
    public SudokuFieldWrapper(int x, int y, SudokuBoard board) {
        this.sudokuBoard = board;
        this.xValue = x;
        this.yValue = y;
        fieldValue.set(this.sudokuBoard.get(xValue, yValue));
    }

    public int getFieldValue() {
        return fieldValue.get();
    }

    public IntegerProperty fieldValueProperty() {
        return fieldValue;
    }

    public void setFieldValue(int fieldValue) {
        this.fieldValue.set(fieldValue);
        sudokuBoard.set(xValue,yValue,fieldValue);
    }

    public SudokuBoard getSudokuBoard() {
        return sudokuBoard;
    }
}
