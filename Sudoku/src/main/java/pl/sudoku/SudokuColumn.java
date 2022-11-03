package pl.sudoku;

import java.util.Vector;

public class SudokuColumn extends SudokuStructure {

    private final SudokuField[] column;

    public SudokuColumn(final SudokuField[] givenColumn) {
        this.column = givenColumn;
    }

    @Override
    public boolean verify() {
        boolean correctColumn = true;
        int fieldValue;
        Vector<Integer> valuesInAColumn = new Vector<>();

        for (int i = 0; i < 9; i++) {
            fieldValue = column[i].getFieldValue();
            if (fieldValue == 0) {
                correctColumn = false;
            } else if (valuesInAColumn.contains(fieldValue)) {
                correctColumn = false;
            } else {
                valuesInAColumn.add(fieldValue);
            }
        }
        return correctColumn;
    }
}
