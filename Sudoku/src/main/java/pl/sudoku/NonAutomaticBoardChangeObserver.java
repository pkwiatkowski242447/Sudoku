package pl.sudoku;

public class NonAutomaticBoardChangeObserver extends Observer {
    private SudokuBoard board;

    @Override
    public void update(SudokuBoard boardAfterChange) {
        if (super.checkBoard(boardAfterChange)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    this.board.set(i, j,boardAfterChange.convertToIntArray()[i][j]);
                }
            }
        }
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

    public NonAutomaticBoardChangeObserver(SudokuBoard board) {
        this.board = new SudokuBoard(board.convertToIntArray());
    }
}
