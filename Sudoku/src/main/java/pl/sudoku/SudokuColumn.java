package pl.sudoku;

import java.util.Vector;

public class SudokuColumn extends SudokuStructure {

    private SudokuField[] column = new SudokuField[9];

    public SudokuColumn(final SudokuField[] column) {
        this.column = column;
    }

    @Override
    public boolean verify() {
        boolean correctColumn = true;
        int fieldValue;
        Vector<Integer> valuesInAColumn = new Vector<>();

        for (int i = 0; i < 9; i++) {
            fieldValue = column[i].getFieldValue();
            if ((fieldValue <= 0 || fieldValue > 9) || valuesInAColumn.contains(fieldValue)) {
                correctColumn = false;
                break;
            } else {
                valuesInAColumn.add(fieldValue);
            }
        }
        return correctColumn;
    }
}
