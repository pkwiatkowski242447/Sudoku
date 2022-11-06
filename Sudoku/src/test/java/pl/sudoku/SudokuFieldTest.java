package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldTest {

    SudokuField exampleSudokuField_1 = new SudokuField();

    @Test
    public void valueSetterTest() {
        exampleSudokuField_1.setFieldValue(5);
        assertEquals(5, exampleSudokuField_1.getFieldValue());
        exampleSudokuField_1.setFieldValue(9);
        assertEquals(9, exampleSudokuField_1.getFieldValue());
        exampleSudokuField_1.setFieldValue(-1);
        assertEquals(9, exampleSudokuField_1.getFieldValue());
        exampleSudokuField_1.setFieldValue(10);
        assertEquals(9, exampleSudokuField_1.getFieldValue());
        exampleSudokuField_1.setFieldValue(0);
        assertEquals(0, exampleSudokuField_1.getFieldValue());
    }

}
