package pl.sudoku;

public class BacktrackingSudokuSolver implements SudokuSolver {


    private void fillSingleMatrix(int numberOfALineMatrix,
                                  int numberOfAColumnMatrix,
                                  SudokuBoard board) {
        int exampleValue;

        for (int i = 0; i < 3; i++) {
            for (int z = 0; z < 3; z++) {
                do {
                    exampleValue = randomNumberGenerator(9);
                } while (!matrixCheck(exampleValue,
                        3 * numberOfALineMatrix + i,
                        3 * numberOfAColumnMatrix + z, board));
                board.set(3 * numberOfALineMatrix + i, 3 * numberOfAColumnMatrix + z, exampleValue);
            }
        }
    }

    private int randomNumberGenerator(int rangeOfGenerating) {
        return (int)(Math.random() * rangeOfGenerating + 1);
    }

    private boolean fillTheRestOfMatrices(int numberOfALine,
                                          int numberOfAColumn,
                                          SudokuBoard board) {
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
            if (verticalCheck(i, numberOfAColumn, board)
                    & horizontalCheck(i, numberOfALine, board)
                    & matrixCheck(i, numberOfALine, numberOfAColumn, board)) {
                board.set(numberOfALine, numberOfAColumn, i);
                if (fillTheRestOfMatrices(numberOfALine, numberOfAColumn + 1, board)) {
                    return true;
                }
                board.set(numberOfALine, numberOfAColumn, 0);
            }
        }
        return false;
    }

    private boolean horizontalCheck(int value, int lineNumber, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (board.get(lineNumber, i) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean verticalCheck(int value, int columnNumber, SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            if (board.get(i, columnNumber) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean matrixCheck(int value, int lineNumber, int columnNumber, SudokuBoard board) {
        int squareX = lineNumber / 3;
        int squareY = columnNumber / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.get(squareX * 3 + i, squareY * 3 + j) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private void cleanBoardBeforeNextGenerate(SudokuBoard board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i, j, 0);
            }
        }
    }

    @Override
    public void solve(SudokuBoard board) {
        cleanBoardBeforeNextGenerate(board);
        for (int i = 0; i < 3; i++) {
            fillSingleMatrix(i, i, board);
        }
        fillTheRestOfMatrices(0, 3, board);
    }

}
