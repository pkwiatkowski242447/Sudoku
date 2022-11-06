package pl.sudoku;

import java.util.Vector;

public abstract class SudokuStructure {

    protected SudokuField[] group;

    public SudokuStructure(SudokuField[] group) {
        this.group = group;
    }

    public boolean verify() {
        Vector<Integer> valuesInAGroup = new Vector<>();
        int valueInAGroup;
        for (int i = 0; i < 9; i++) {
            valueInAGroup = group[i].getFieldValue();
            if (valuesInAGroup.contains(valueInAGroup) || valueInAGroup == 0) {
                return false;
            } else {
                valuesInAGroup.add(valueInAGroup);
            }
        }
        return true;
    }
}
