package pl.sudoku;

public class NonAutomaticBoardChangeBoardChangeObserver extends BoardChangeObserver {

    public NonAutomaticBoardChangeBoardChangeObserver(final SudokuBoard board) {
        super(board);
    }

    @Override
    public void update(final SudokuBoard boardAfterChange) {
        if (super.checkBoard(boardAfterChange)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    this.board.set(i, j,boardAfterChange.convertToIntList().get(i * 9 + j));
                }
            }
        }
    }
}
