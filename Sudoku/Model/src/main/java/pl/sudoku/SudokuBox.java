package pl.sudoku;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBox extends SudokuStructure implements Cloneable {

    public SudokuBox(final List<SudokuField> box) {
        super(box);
    }

    @Override
    public SudokuBox clone() {
        List<SudokuField> boxclone = new ArrayList<SudokuField>(group.size());
        for (int i = 0; i < group.size(); i++) {
            boxclone.add(this.getGroup().get(i));
        }
        return new SudokuBox(boxclone);
    }

    @Override
    public String toString() {
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                stringBuilder.append(this.getValueInStructure(3 * i + j));
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
