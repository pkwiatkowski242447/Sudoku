package pl.sudoku;

import java.util.Vector;

public class SudokuBox extends SudokuStructure {

    private final SudokuField[][] box;

    public SudokuBox(final SudokuField[][] givenBox) {
        this.box = givenBox;
    }

    @Override
    public boolean verify() {
        boolean correctBox = true;
        int fieldValue;
        Vector<Integer> valuesInABox = new Vector<>();

        for (int i = 0; i < 3; i++) {
            for (int z = 0; z < 3; z++) {
                fieldValue = box[i][z].getFieldValue();
                if (fieldValue == 0) {
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
