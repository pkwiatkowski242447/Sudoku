package pl.sudoku;

public class SudokuBoard {
    private final SudokuField[][] board = new SudokuField[9][9];
    private SudokuSolver solver;

    private void generate() {
        for (int i = 0; i < 9; i++) {
          for (int h = 0; h < 9; h++) {
              board[i][h] = new SudokuField();
          }
        }
    }

    public SudokuBoard(SudokuSolver solver1) {
        generate();
        solver = solver1;
    }

    public SudokuBoard(int[][] sudokuBoard) {
        generate();
        for (int i = 0; i < 9; i++) {
           for (int h = 0; h < 9; h++) {
               this.set(i,h,sudokuBoard[i][h]);
           }
        }
    }

    public void solveGame() {
        solver.solve(this);
    }

    public int get(int x, int y) {
      return board[x][y].getFieldValue();
    }

    public void set(int x, int y, int value) {
        board[x][y].setFieldValue(value);
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

    public boolean checkBoard() {
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
                if (!getBox(i,j).verify()) {
                    correctBoard = false;
                }
            }
        }
        return correctBoard;
    }

    public SudokuRow getRow(int x) {
        SudokuField[]row = new SudokuField[9];
        System.arraycopy(board[x], 0, row, 0, 9);
        return new SudokuRow(row);
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[]column = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            column[i] = board[i][x];
        }
        return new SudokuColumn(column);
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[]box = new SudokuField[9];
        int matrixFirstLine = 3 * (x / 3);
        int matrixFirstColumn = 3 * (y / 3);
        for (int i = 0; i < 3; i++) {
            for (int h = 0; h < 3; h++) {
                box[3 * i + h] = board[matrixFirstLine + i][matrixFirstColumn + i];
            }
        }
        return new SudokuBox(box);
    }
}