package pl.sudoku;

public class SudokuField {

    private int value;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value1) {
        if (value1 >= 0 && value1 <= 9) {
            value = value1;
        }
    }

}
