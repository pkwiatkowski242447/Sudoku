package org.example.view;

import pl.sudoku.SudokuBoard;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Difficulty {
    EASY(40), MEDIUM(50), HARD(60);

    private final int enumValue;

    Difficulty(int i) {
        enumValue = i;
    }

    public void removeSomeFields(SudokuBoard sudokuBoard) {
        int numberOfFieldsToRemove = enumValue;
        int xValue = 0;
        int yValue = 0;
        List<Integer> values = Arrays.asList(1,2,3,4,5,6,7,8,9);
        do {
            Collections.shuffle(values);
            xValue = values.get(0);
            yValue = values.get(0);
            if (sudokuBoard.get(xValue, yValue) != 0) {
                sudokuBoard.set(xValue,yValue,0);
                numberOfFieldsToRemove--;
            }
        } while (numberOfFieldsToRemove > 0);
    }

    public static Difficulty toRealDiff(String input) {
        Difficulty difficulty = null;
        if (input != null) {
            switch (input) {
                case "Prosty" -> difficulty = Difficulty.EASY;
                case "Średni" -> difficulty = Difficulty.MEDIUM;
                case "Trudny" -> difficulty = Difficulty.HARD;
            }
        }
        return difficulty;
    }
}
