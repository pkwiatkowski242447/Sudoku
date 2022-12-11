package pl.sudoku;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuColumn extends SudokuStructure implements Cloneable {

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


    @Override
    public SudokuColumn clone() {
        List<SudokuField> tempList = new ArrayList<>(this.getSudokuFieldList());
        return new SudokuColumn(tempList);
    }
}
