package pl.sudoku;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBox extends SudokuStructure implements Cloneable {

    public SudokuBox(final List<SudokuField> box) {
        super(box);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                stringBuilder.append(this.getValue(3 * i + j));
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    @Override
    public SudokuBox clone() {
        List<SudokuField> tempList = new ArrayList<>(this.getSudokuFieldList());
        return new SudokuBox(tempList);
    }
}
