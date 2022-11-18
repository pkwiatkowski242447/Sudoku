package pl.sudoku;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class SudokuStructure {

    protected List<SudokuField> group;

    public SudokuStructure(List<SudokuField> group) {
        this.group = group;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuStructure that = (SudokuStructure) o;

        return new EqualsBuilder().append(group, that.group).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(group).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("group", group)
                .toString();
    }
}
