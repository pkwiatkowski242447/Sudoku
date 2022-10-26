package pl.sudoku;

import java.util.Vector;

public class SudokuRow extends SudokuStructure {

    private final SudokuField[] row;

    public SudokuRow(final SudokuField[] givenRow) {
        this.row = givenRow;
    }

    @Override
    public boolean verify() {
        boolean correctRow = true;
        int fieldValue;
        Vector<Integer> valuesInARow = new Vector<>();

        for (int i = 0; i < 9; i++) {
            fieldValue = row[i].getFieldValue();
            if (valuesInARow.contains(fieldValue)) {
                correctRow = false;
            } else {
                valuesInARow.add(fieldValue);
            }
        }
        return correctRow;
    }
}
