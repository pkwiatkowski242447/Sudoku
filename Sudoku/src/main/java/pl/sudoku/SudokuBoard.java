package pl.sudoku;

import java.util.Vector;

public class SudokuBoard {
    private final int[][] board = new int [9][9];

    public SudokuBoard(int[][] sudokuBoard) {
        for (int i = 0; i < 9; i++) {
            System.arraycopy(sudokuBoard[i], 0, board[i], 0, 9);
        }
    }

    public SudokuBoard() {
    }

    public void fillBoard() {
        fillDiagonalMatrices();
        fillTheRestOfMatrices(0,3);
    }

    public int randomNumberGenerator(int rangeOfGenerating) {
        return (int)(Math.random() * rangeOfGenerating + 1);
    }

    public void fillDiagonalMatrices() {
        for (int i = 0; i < 3; i++) {
            fillSingleMatrix(i, i);
        }
    }

    public void fillSingleMatrix(int numberOfALineMatrix, int numberOfAColumnMatrix) {
        int exampleValue;

        for (int i = 0; i < 3; i++) {
            for (int z = 0; z < 3; z++) {
                do {
                    exampleValue = randomNumberGenerator(9);
                } while (!matrixCheck(exampleValue,
                        (3 * numberOfALineMatrix + i),
                        (3 * numberOfAColumnMatrix + z)));
                board[3 * numberOfALineMatrix + i][3 * numberOfAColumnMatrix + z] = exampleValue;
            }
        }
    }

    public boolean fillTheRestOfMatrices(int numberOfALine, int numberOfAColumn) {
        if (numberOfALine < 8 && numberOfAColumn >= 9) {
            numberOfALine++;
            numberOfAColumn = 0;
        }

        if (numberOfALine < 3) {
            if (numberOfAColumn < 3) {
                numberOfAColumn = 3;
            }
        } else if (numberOfALine < 6) {
            if (numberOfAColumn == 3) {
                numberOfAColumn = 6;
            }
        } else {
            if (numberOfAColumn == 6) {
                numberOfALine++;
                numberOfAColumn = 0;
            }
            if (numberOfALine >= 9) {
                return true;
            }
        }

        for (int i = 1; i <= 9; i++) {
            if ((verticalCheck(i, numberOfAColumn)
                    && horizontalCheck(i, numberOfALine))
                    && matrixCheck(i, numberOfALine, numberOfAColumn)) {
                board[numberOfALine][numberOfAColumn] = i;
                if (fillTheRestOfMatrices(numberOfALine, numberOfAColumn + 1)) {
                    return true;
                }
                board[numberOfALine][numberOfAColumn] = 0;
            }
        }
        return false;
    }

    public int getValue(int x, int y) {
        if ((x >= 9 || y >= 9) || (x < 0 || y < 0)) {
            return 0;
        } else {
            return board[x][y];
        }
    }

    public boolean horizontalCheck(int value, int lineNumber) {
        for (int i = 0; i < 9; i++) {
            if (board[lineNumber][i] == value) {
                return false;
            }
        }
        return true;
    }

    public boolean verticalCheck(int value, int columnNumber) {
        for (int i = 0; i < 9; i++) {
            if (board[i][columnNumber] == value) {
                return false;
            }
        }
        return true;
    }

    public boolean matrixCheck(int value, int lineNumber, int columnNumber) {
        int squareX = lineNumber / 3;
        int squareY = columnNumber / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[squareX * 3 + i][squareY * 3 + j] == value) {
                    return false;
                }
            }
        }
        return true;
    }

    public String showSudokuBoard() {
        String sudokuOutput = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuOutput += board[i][j] + " ";
            }
            sudokuOutput += '\n';
        }
        return sudokuOutput;
    }

    public boolean checkValuesInALine(int numberOfALine) {

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

    public boolean checkValuesInAColumn(int numberOfAColumn) {

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

    public boolean checkValuesInAMatrix(int numberOfALine, int numberOfAColumn) {

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
