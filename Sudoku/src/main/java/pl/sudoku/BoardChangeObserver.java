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
    public boolean equals(Object someObject) {
        if (someObject == this) {
            return true;
        }
        if (someObject == null) {
            return false;
        }
        if (someObject.getClass() != this.getClass()) {
            return false;
        }
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        return equalsBuilder.append(this.board,
                ((BoardChangeObserver) someObject).board).isEquals();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(47, 91);
        return hashCodeBuilder.append(this.board).hashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        toStringBuilder.append("Type: " + this.getClass());
        toStringBuilder.append("Board: ");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                toStringBuilder.append(this.getBoard()[i][j]);
            }
            toStringBuilder.append('\n');
        }
        return toStringBuilder.toString();
    }
}
