package pl.sudoku;

import java.util.Vector;

public abstract class SudokuStructure {

    protected SudokuField[] structure;

    public SudokuStructure(final SudokuField[] structure) {
        this.structure = structure;
    }

    public boolean verify() {
        boolean correctStructure = true;
        Vector<Integer> valuesInStructure = new Vector<>();
        int sudokuFieldValue;
        for (int i = 0; i < 9; i++) {
            sudokuFieldValue = structure[i].getFieldValue();
            if (sudokuFieldValue == 0) {
                correctStructure = false;
            } else if (valuesInStructure.contains(sudokuFieldValue)) {
                correctStructure = false;
            } else {
                valuesInStructure.add(sudokuFieldValue);
            }

        }
        return correctStructure;
    }
}
