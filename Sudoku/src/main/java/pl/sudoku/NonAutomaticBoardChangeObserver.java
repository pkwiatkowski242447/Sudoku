package pl.sudoku;

public class NonAutomaticBoardChangeObserver extends Extender {

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

    public NonAutomaticBoardChangeObserver(final SudokuBoard board) {
        super(board);
    }
}
