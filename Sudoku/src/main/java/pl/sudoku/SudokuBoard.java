package pl.sudoku;

import java.util.Vector;

public class SudokuBoard {
    private final int[][] board = new int [9][9];
    private SudokuSolver solver;

    public SudokuBoard(int[][] sudokuBoard) {
        for (int i = 0; i < 9; i++) {
            System.arraycopy(sudokuBoard[i], 0, board[i], 0, 9);
        }
    }

    public SudokuBoard() {
        solver = new BacktrackingSudokuSolver();
    }

    public void solveGame() {
        solver.solve(this);
    }

    public int get(int x, int y) {
        if ((x >= 9 || y >= 9) || (x < 0 || y < 0)) {
            return 0;
        } else {
            return board[x][y];
        }
    }

    public void set(int x, int y, int value) {
        if (value >= 0 && value <= 9) {
            board[x][y] = value;
        }
    }

    public String toString() {
        String sudokuOutput = "";
        sudokuOutput += "|-----------------------|\n";
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 3; l++) {
                sudokuOutput += "| ";
                for (int j = 0; j < 3; j++) {
                    for (int z = 0; z < 3; z++) {
                        sudokuOutput += board[i * 3 + l][j * 3 + z] + " ";
                    }
                    sudokuOutput += "| ";
                }
                sudokuOutput += '\n';
            }
            sudokuOutput += "|-----------------------|\n";
        }
        return sudokuOutput;
    }

    public boolean checkBoard() {
        boolean correctBoard = true;
        for (int i = 0; i < 9; i++) {
            if (!checkValuesInALine(i)) {
                correctBoard = false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (!checkValuesInAColumn(i)) {
                correctBoard = false;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!checkValuesInAMatrix(3 * i, 3 * j)) {
                    correctBoard = false;
                }
            }
        }
        return correctBoard;
    }

    private boolean checkValuesInALine(int numberOfALine) {

        Vector<Integer> valuesInALine = new Vector<>();

        for (int i = 0; i < 9; i++) {
            if (valuesInALine.contains(board[numberOfALine][i])) {
                return false;
            } else {
                valuesInALine.add(board[numberOfALine][i]);
            }
        }
        return true;
    }

    private boolean checkValuesInAColumn(int numberOfAColumn) {

        Vector<Integer> valuesInAColumn = new Vector<>();

        for (int i = 0; i < 9; i++) {
            if (valuesInAColumn.contains(board[i][numberOfAColumn])) {
                return false;
            } else {
                valuesInAColumn.add(board[i][numberOfAColumn]);
            }
        }
        return true;
    }

    private boolean checkValuesInAMatrix(int numberOfALine, int numberOfAColumn) {

        int matrixFirstLine = 3 * (numberOfALine / 3);
        int matrixFirstColumn = 3 * (numberOfAColumn / 3);

        Vector<Integer> valuesInAMatrix = new Vector<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (valuesInAMatrix.contains(board[matrixFirstLine + i][matrixFirstColumn + j])) {
                    return false;
                } else {
                    valuesInAMatrix.add(board[matrixFirstLine + i][matrixFirstColumn + j]);
                }
            }
        }
        return true;
    }
}
