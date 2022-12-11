package pl.sudoku;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuRow extends SudokuStructure implements Cloneable {

    public SudokuRow(final List<SudokuField> row) {
        super(row);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        for (int i = 0; i < 9; i++) {
            stringBuilder.append(this.getValue(i));
        }
        return stringBuilder.toString();
    }

<<<<<<< HEAD

    @Override
    public SudokuRow clone() {
        List<SudokuField> tempList = new ArrayList<>(this.getSudokuFieldList());
        return new SudokuRow(tempList);
=======
    @Override
    protected SudokuRow clone() {
        List<SudokuField> rowclone = new ArrayList<SudokuField>(group.size());
        for (int i = 0; i < group.size(); i++) {
            rowclone.add(this.getGroup().get(i));
        }
        return new SudokuRow(rowclone);
>>>>>>> 9e138b33089c356a5466a028177ad5a72ab7f608
    }
}
