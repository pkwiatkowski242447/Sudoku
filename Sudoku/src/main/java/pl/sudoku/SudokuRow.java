package pl.sudoku;

import java.util.Vector;

public class SudokuRow extends SudokuStructure {

    private SudokuField[] row = new SudokuField[9];

    public SudokuRow(final SudokuField[] row) {
        this.row = row;
    }

    @Override
    public boolean verify() {
        boolean correctRow = true;
        int fieldValue;
        Vector<Integer> valuesInARow = new Vector<>();

        for (int i = 0; i < 9; i++) {
            fieldValue = row[i].getFieldValue();
            if ((fieldValue <= 0 || fieldValue > 9) || valuesInARow.contains(fieldValue)) {
                correctRow = false;
                break;
            } else {
                valuesInARow.add(fieldValue);
            }
        }
        return correctRow;
    }
}
