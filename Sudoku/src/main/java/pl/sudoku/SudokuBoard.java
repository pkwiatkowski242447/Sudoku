package pl.sudoku;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBoard {

    private final SudokuField[][] board = new SudokuField[9][9];

    private final SudokuSolver solver;
    private Set<Observer> setOfObservers = new HashSet<>();


    private void generateSudokuFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
    }

    public SudokuBoard(int[][] sudokuBoard) {
        solver = new BacktrackingSudokuSolver();
        boolean correctBoard = true;
        generateSudokuFields();

        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                if (sudokuBoard[i][z] <= 0) {
                    correctBoard = false;
                } else if (sudokuBoard[i][z] > 9) {
                    correctBoard = false;
                }
            }
        }

        if (correctBoard) {
            for (int i = 0; i < 9; i++) {
                for (int z = 0; z < 9; z++) {
                    this.set(i, z, sudokuBoard[i][z]);
                }
            }
        } else {
            this.solveGame();
        }

    }

    public SudokuBoard(SudokuSolver solver1) {
        generateSudokuFields();
        solver = solver1;
    }

    public void solveGame() {
        solver.solve(this);
        checkBoard();
    }

    public int get(int x, int y) {
        if (x >= 9 || y >= 9 || x < 0 || y < 0) {
            return 0;
        } else {
            return board[x][y].getFieldValue();
        }
    }

    public void set(int x, int y, int value) {
        if (value >= 0 && value <= 9) {
            board[x][y].setFieldValue(value);
        }
        if (value == this.get(x, y)) {
            notifyObservers();
        }
    }

    public Set<Observer> getSetOfObservers() {
        return Collections.unmodifiableSet(setOfObservers);
    }

    public int[][] convertToIntArray() {
        int[][] finalArray = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                finalArray[i][j] = board[i][j].getFieldValue();
            }
        }
        return finalArray;
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
                if (!getBox(3 * i, 3 * j).verify()) {
                    correctBoard = false;
                }
            }
        }
        return correctBoard;
    }

    public SudokuRow getRow(int y) {
        if (y >= 0 & y < 9) {
            List<SudokuField> row = Arrays.asList(new SudokuField[9]);
            for (int i = 0; i < 9; i++) {
                row.set(i, new SudokuField());
            }
            for (int i = 0; i < 9; i++) {
                row.get(i).setFieldValue(get(y, i));
            }
            return new SudokuRow(row);
        } else {
            return null;
        }
    }

    public SudokuColumn getColumn(int x) {
        if (x >= 0 & x < 9) {
            List<SudokuField> column = Arrays.asList(new SudokuField[9]);
            for (int i = 0; i < 9; i++) {
                column.set(i, new SudokuField());
            }
            for (int i = 0; i < 9; i++) {
                column.get(i).setFieldValue(get(i, x));
            }
            return new SudokuColumn(column);
        } else {
            return null;
        }
    }

    public SudokuBox getBox(int x, int y) {
        if ((x >= 0 & y >= 0) & (x < 9 & y < 9)) {
            List<SudokuField> box = Arrays.asList(new SudokuField[9]);
            int matrixFirstLine = 3 * (x / 3);
            int matrixFirstColumn = 3 * (y / 3);
            for (int i = 0; i < 9; i++) {
                box.set(i, new SudokuField());
            }
            for (int i = 0; i < 3; i++) {
                for (int f = 0; f < 3; f++) {
                    box.get(i * 3 + f)
                            .setFieldValue(get(matrixFirstLine + i, matrixFirstColumn + f));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder().append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board", board)
                .toString();
    }
}
