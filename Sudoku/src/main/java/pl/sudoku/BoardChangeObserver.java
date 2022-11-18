package pl.sudoku;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class BoardChangeObserver implements Observer {

    protected SudokuBoard board;

    public BoardChangeObserver(final SudokuBoard board) {
        this.board = new SudokuBoard(board.convertToIntArray());
    }

    public abstract void update(final SudokuBoard sudokuBoard);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoardChangeObserver that = (BoardChangeObserver) o;

        return new EqualsBuilder().append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board", board)
                .toString();
    }
}
