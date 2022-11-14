package pl.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SudokuStructure {

    protected List<SudokuField> group;

    public SudokuStructure(List<SudokuField> group) {
        this.group = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            this.group.set(i, new SudokuField());
        }
        for (int i = 0; i < 9; i++) {
            this.group.get(i).setFieldValue(group.get(i).getFieldValue());
        }
    }

    public boolean verify() {
        List<Integer> valuesInAGroup = new ArrayList<>();
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
