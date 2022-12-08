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
    protected SudokuColumn clone() {
        List<SudokuField> columnclone = new ArrayList<SudokuField>(group.size());
         for (int i = 0; i < group.size(); i++) {
            columnclone.add(this.getGroup().get(i));
        }
        return new SudokuColumn(columnclone);
    }
}
