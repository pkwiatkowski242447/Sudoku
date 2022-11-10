package pl.sudoku;

import java.util.List;
import java.util.Vector;

public abstract class SudokuStructure {

    protected List<SudokuField> group;

    public SudokuStructure(List<SudokuField> group) {
        this.group = group;
    }

    public boolean verify() {
        Vector<Integer> valuesInAGroup = new Vector<>();
        int valueInAGroup;
        for (int i = 0; i < 9; i++) {
            valueInAGroup = group.get(i).getFieldValue();
            if (valuesInAGroup.contains(valueInAGroup) || valueInAGroup == 0) {
                return false;
            } else {
                valuesInAGroup.add(valueInAGroup);
            }
        }
        return true;
    }
}
