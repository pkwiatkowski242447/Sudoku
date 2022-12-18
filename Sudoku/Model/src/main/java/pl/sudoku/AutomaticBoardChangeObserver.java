package pl.sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

public class AutomaticBoardChangeObserver extends BoardChangeObserver {

    private final Logger log = LoggerFactory.getLogger(AutomaticBoardChangeObserver.class);

    public AutomaticBoardChangeObserver(final SudokuBoard board) {
        super(board);
    }

    @Override
    public void update(final SudokuBoard boardAfterChange) {
       ResourceBundle resourceBundle = ResourceBundle.getBundle("Language");
        if (!super.checkBoard(boardAfterChange)) {
           log.info(resourceBundle.getString("AutomaticBoardChangeObserverUpdate"));
        } else {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    this.board.set(i, j, boardAfterChange.convertToIntArray()[i][j]);
                }
            }
        }
    }
}
