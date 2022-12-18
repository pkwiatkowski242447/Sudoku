package pl.sudoku;

import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutomaticBoardChangeObserver extends BoardChangeObserver {

    private static final Logger logger = LoggerFactory
            .getLogger(AutomaticBoardChangeObserver.class);

    public AutomaticBoardChangeObserver(final SudokuBoard board) {
        super(board);
    }

    @Override
    public void update(final SudokuBoard boardAfterChange) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("ProKomBundle");
        if (!super.checkBoard(boardAfterChange)) {
            logger.info(resourceBundle.getString("incorrectSudokuFill"));
        } else {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    this.board.set(i, j, boardAfterChange.convertToIntArray()[i][j]);
                }
            }
        }
    }
}
