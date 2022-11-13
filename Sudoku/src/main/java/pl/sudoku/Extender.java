package pl.sudoku;

public abstract class Extender implements Observer {
    protected SudokuBoard board;

    public Extender(final SudokuBoard board) {
        this.board = new SudokuBoard(board.convertToIntArray());
    }

    @Override
    public abstract void update(SudokuBoard exampleBoard);

    protected boolean checkBoard(final SudokuBoard someBoard) {
        boolean correctBoard = true;
        for (int i = 0; i < 9; i++) {
            if (!someBoard.getRow(i).verify()) {
                correctBoard = false;
            } else if (!someBoard.getColumn(i).verify()) {
                correctBoard = false;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!someBoard.getBox(3 * i, 3 * j).verify()) {
                    correctBoard = false;
                }
            }
        }
        return correctBoard;
    }

    public int[][] getBoard() {
        int[][] board1 = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board1[i][j] = board.get(i, j);
            }
        }
        return board1;
    }
}