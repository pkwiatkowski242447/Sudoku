package pl.sudoku;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class SudokuBoard {

    private final SudokuField[][] board = new SudokuField[9][9];
    private final SudokuSolver solver;
    private Vector<Observer> vectorOfObservers = new Vector<>();

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

    public List<Observer> getObserversList() {
        return Collections.unmodifiableList(vectorOfObservers);
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

    public String toString() {
        String sudokuOutput = "";
        sudokuOutput += "|-----------------------|\n";
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 3; l++) {
                sudokuOutput += "| ";
                for (int j = 0; j < 3; j++) {
                    for (int z = 0; z < 3; z++) {
                        sudokuOutput += board[i * 3 + l][j * 3 + z].getFieldValue() + " ";
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
        SudokuField[] row = new SudokuField[9];
        System.arraycopy(board[y], 0, row, 0, 9);
        return new SudokuRow(List.of(row));
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] column = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            column[i] = board[i][x];
        }
        return new SudokuColumn(List.of(column));
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] box = new SudokuField[9];
        int matrixFirstLine = 3 * (x / 3);
        int matrixFirstColumn = 3 * (y / 3);
        for (int i = 0; i < 3; i++) {
            System.arraycopy(board[matrixFirstLine + i], matrixFirstColumn, box, 3 * i, 3);
        }
        return new SudokuBox(List.of(box));
    }

    public void addObserver(Observer observer) {
        if (!vectorOfObservers.contains(observer) & observer != null) {
            vectorOfObservers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        if (observer != null & vectorOfObservers.contains(observer)) {
            vectorOfObservers.remove(observer);
        }
    }

    public void notifyObservers() {
        for (Observer observer : vectorOfObservers) {
            observer.update(this);
        }
    }

}