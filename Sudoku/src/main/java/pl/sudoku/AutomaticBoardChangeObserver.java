package pl.sudoku;

public class AutomaticBoardChangeObserver extends Observer {

    @Override
    public void update(SudokuBoard boardAfterChange) {
        if (!super.checkBoard(boardAfterChange)) {
            System.out.print("Nieprawidłowe uzupełnienie planszy.");
        } else {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    this.board.set(i, j, boardAfterChange.convertToIntArray()[i][j]);
                }
            }
        }
    }

    public AutomaticBoardChangeObserver(SudokuBoard board) {
        super(board);
    }
}
