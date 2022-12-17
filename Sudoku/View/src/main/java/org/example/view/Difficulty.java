package org.example.view;

import pl.sudoku.SudokuBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Difficulty {
    EASY(40), MEDIUM(50), HARD(60);

    private final int enumValue;

    Difficulty(int i) {
        enumValue = i;
    }

    public int getEnumValue() {
        return enumValue;
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

    public void removeSomeFields(SudokuBoard sudokuBoard) {
        int numberOfFieldsToRemove = this.getEnumValue();
        int xValue = 0;
        int yValue = 0;


        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            values.add(i);
        }

        Collections.shuffle(values);

        for (int i = 0; i < numberOfFieldsToRemove; i++) {
            xValue = (int) (values.get(i) / 9);
            yValue = values.get(i) % 9;
            sudokuBoard.set(xValue, yValue, 0);
        }
    }
}
