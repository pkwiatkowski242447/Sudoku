package pl.sudoku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.sudoku.exceptions.SudokuStructureInvalidIndex;

public abstract class SudokuStructure implements Serializable {

    private final List<SudokuField> group;

    public SudokuStructure(final List<SudokuField> group) {
        this.group = Arrays.asList(new SudokuField[9]);
        for (int i = 0; i < 9; i++) {
            this.group.set(i, new SudokuField());
        }
        for (int i = 0; i < 9; i++) {
            this.group.get(i).setFieldValue(group.get(i).getFieldValue());
        }
    }

    public int getValue(int someIndex) {
        if (someIndex >= 0 & someIndex < 9) {
            return group.get(someIndex).getFieldValue();
        } else {
            throw new SudokuStructureInvalidIndex("Podana współrzędna jest poza zakresem");
        }
    }

    public List<SudokuField> getSudokuFieldList() {
        return Collections.unmodifiableList(group);
    }

    public boolean verify() {
        List<Integer> valuesInAGroup = new ArrayList<>();
        int valueInAGroup;
        for (int i = 0; i < 9; i++) {
            valueInAGroup = group.get(i).getFieldValue();
            if (valuesInAGroup.contains(valueInAGroup) & valueInAGroup != 0) {
                return false;
            } else {
                valuesInAGroup.add(valueInAGroup);
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        SudokuStructure that = (SudokuStructure) o;

        return new EqualsBuilder().append(group, that.group).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(group).toHashCode();
    }

}
