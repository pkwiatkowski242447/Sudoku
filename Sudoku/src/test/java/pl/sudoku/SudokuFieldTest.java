/*
 * Project name: Programowanie komponentowe - Sudoku
 *
 * Copyright © ${inceptionYear} ${organization.name}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.sudoku;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class SudokuFieldTest {

    Integer[] correctBoard = {
            5,3,4,6,7,8,9,1,2,
            6,7,2,1,9,5,3,4,8,
            1,9,8,3,4,2,5,6,7,
            8,5,9,7,6,1,4,2,3,
            4,2,6,8,5,3,7,9,1,
            7,1,3,9,2,4,8,5,6,
            9,6,1,5,3,7,2,8,4,
            2,8,7,4,1,9,6,3,5,
            3,4,5,2,8,6,1,7,9
    };

    List<Integer> sudokuFieldList = Arrays.asList(correctBoard);

    SudokuField exampleSudokuField_1 = new SudokuField();

    @Test
    public void valueGetterTest() {
        SudokuField[][] table = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                table[i][j] = new SudokuField();
                table[i][j].setFieldValue(sudokuFieldList.get(i * 9 + j));
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(sudokuFieldList.get(i * 9 + j), table[i][j].getFieldValue());
            }
        }
        assertEquals(0, exampleSudokuField_1.getFieldValue());
    }
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
