package pl.sudoku;

public class SudokuBoard {
    private final SudokuField[][] board = new SudokuField[9][9];
    private SudokuSolver solver;

    private void generateSudokuFields() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
    }

    public SudokuBoard(int[][] sudokuBoard) {
        boolean correctBoard = true;
        generateSudokuFields();

        for (int i = 0; i < 9; i++) {
            for (int z = 0; z < 9; z++) {
                if (sudokuBoard[i][z] <= 0 && sudokuBoard[i][z] > 9) {
                    correctBoard = false;
                    break;
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
    }

    public int get(int x, int y) {
        if ((x >= 9 || y >= 9) || (x < 0 || y < 0)) {
            return 0;
        } else {
            return board[x][y].getFieldValue();
        }
    }

    public void set(int x, int y, int value) {
        if (value >= 0 && value <= 9) {
            board[x][y].setFieldValue(value);
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
        return new SudokuRow(row);
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] column = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            column[i] = board[i][x];
        }
        return new SudokuColumn(column);
    }

    public SudokuBox getBox(int x, int y) {

        int firstBoxRow = 3 * (x / 3);
        int firstBoxColumn = 3 * (y / 3);

        SudokuField[][] box = new SudokuField[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                box[i][j] = board[firstBoxRow + i][firstBoxColumn + j];

            }
        }
        return new SudokuBox(box);
    }

}