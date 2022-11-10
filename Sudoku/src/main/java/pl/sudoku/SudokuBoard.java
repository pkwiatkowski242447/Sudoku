package pl.sudoku;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuBoard {
    private final List<SudokuField> board = Arrays.asList(new SudokuField[81]);
    private final SudokuSolver solver;
    private Set<Observer> setOfObservers = new HashSet<>();

    private void generateSudokuFields(int amount, List<SudokuField> list) {
        for (int i = 0; i < amount; i++) {
            list.set(i, new SudokuField());
        }
    }

    public SudokuBoard(List<Integer> sudokuBoard) {
        solver = new BacktrackingSudokuSolver();
        boolean correctBoard = true;
        generateSudokuFields(81, board);

        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                if (sudokuBoard.get(i * 9 + z) <= 0) {
                    correctBoard = false;
                } else if (sudokuBoard.get(i * 9 + z) > 9) {
                    correctBoard = false;
                }
            }
        }

        if (correctBoard) {
            for (int i = 0; i < 9; i++) {
                for (int z = 0; z < 9; z++) {
                    this.set(i, z, sudokuBoard.get(i * 9 + z));
                }
            }
        } else {
            this.solveGame();
        }

    }

    public SudokuBoard(SudokuSolver solver1) {
        generateSudokuFields(81, board);
        solver = solver1;
    }

    public void solveGame() {
        solver.solve(this);
    }

    public int get(int x, int y) {
        if ((x >= 9 || y >= 9) || (x < 0 || y < 0)) {
            return 0;
        } else {
            return board.get(x * 9 + y).getFieldValue();
        }
    }

    public void set(int x, int y, int value) {
        if (value >= 0 && value <= 9) {
            board.get(x *  9 + y).setFieldValue(value);
        }
        if (value == this.get(x, y)) {
            notifyObservers();
        }
    }

    public Set<Observer> getSetOfObservers() {
        return Collections.unmodifiableSet(setOfObservers);
    }

    public List<Integer> convertToIntList() {
        List<Integer> finalList = Arrays.asList(new Integer[81]);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                finalList.set(i * 9 + j, board.get(i * 9 + j).getFieldValue());
            }
        }
        return finalList;
    }

    public String toString() {
        String sudokuOutput = "";
        sudokuOutput += "|-----------------------|\n";
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 3; l++) {
                sudokuOutput += "| ";
                for (int j = 0; j < 3; j++) {
                    for (int z = 0; z < 3; z++) {
                        sudokuOutput += get(i * 3 + l, j * 3 + z) + " ";
                    }
                    sudokuOutput += "| ";
                }
                sudokuOutput += '\n';
            }
            sudokuOutput += "|-----------------------|\n";
        }
        return sudokuOutput;
    }

    public Method getCheckBoard() throws NoSuchMethodException {
        Method method = this.getClass().getDeclaredMethod("checkBoard");
        method.setAccessible(true);
        return method;
    }

    private boolean checkBoard() {
        boolean correctBoard = true;
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify()) {
                correctBoard = false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!getColumn(i).verify()) {
                correctBoard = false;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!getBox(3 * i,3 * j).verify()) {
                    correctBoard = false;
                }
            }
        }
        return correctBoard;
    }

    public SudokuRow getRow(int y) {
        if (y >= 0 & y < 9) {
            List<SudokuField> row = Arrays.asList(new SudokuField[9]);
            generateSudokuFields(9, row);
            for (int i = 0; i < 9; i++) {
                row.get(i).setFieldValue(board.get(y * 9 + i).getFieldValue());
            }
            return new SudokuRow(row);
        } else {
            return null;
        }
    }

    public SudokuColumn getColumn(int x) {
        if (x >= 0 & x < 9) {
            List<SudokuField> column = Arrays.asList(new SudokuField[9]);
            generateSudokuFields(9, column);
            for (int i = 0; i < 9; i++) {
                column.get(i).setFieldValue(board.get(i * 9 + x).getFieldValue());
            }
            return new SudokuColumn(column);
        } else {
            return null;
        }
    }

    public SudokuBox getBox(int x, int y) {
        if ((x >= 0 & y >= 0) & (x < 9 & y < 9)) {
            List<SudokuField> box = Arrays.asList(new SudokuField[9]);
            generateSudokuFields(9, box);
            int matrixFirstLine = 3 * (x / 3);
            int matrixFirstColumn = 3 * (y / 3);
            for (int i = 0; i < 3; i++) {
                for (int f = 0; f < 3; f++) {
                    box.get(i * 3 + f)
                            .setFieldValue(board.get(matrixFirstLine * 9
                                            + matrixFirstColumn + i * 9 + f)
                                    .getFieldValue());
                }
            }
            return new SudokuBox(box);
        } else {
            return null;
        }
    }

    public void addObserver(Observer observer) {
        if (observer != null) {
            setOfObservers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        if (observer != null) {
            setOfObservers.remove(observer);
        }
    }

    public void notifyObservers() {
        for (Observer observer : setOfObservers) {
            observer.update(this);
        }
    }

}