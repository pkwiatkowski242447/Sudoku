package pl.sudoku;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuRow extends SudokuStructure {

    public SudokuRow(final List<SudokuField> row) {
        super(row);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        for (int i = 0; i < 9; i++) {
            stringBuilder.append(this.getValueInStructure(i));
        }
        return stringBuilder.toString();
    }
}
