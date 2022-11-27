package pl.sudoku;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuColumn extends SudokuStructure {

    public SudokuColumn(final List<SudokuField> column) {
        super(column);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(null);
        for (int i = 0; i < 9; i++) {
            stringBuilder.append(i);
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
