package pl.sudoku;

import java.util.Vector;

public class SudokuBox extends SudokuStructure {

    private SudokuField[][] box = new SudokuField[3][3];

    public SudokuBox(final SudokuField[][] box) {
        this.box = box;
    }

    @Override
    public boolean verify() {
        boolean correctBox = true;
        int fieldValue;
        Vector<Integer> valuesInABox = new Vector<>();

        for (int i = 0; i < 3; i++) {
            for (int z = 0; z < 3; z++) {
                fieldValue = box[i][z].getFieldValue();
                if (fieldValue <= 0 || fieldValue > 9) {
                    correctBox = false;
                } else if (valuesInABox.contains(fieldValue)) {
                    correctBox = false;
                } else {
                    valuesInABox.add(fieldValue);
                }
            }
        }
        return correctBox;
    }
}
