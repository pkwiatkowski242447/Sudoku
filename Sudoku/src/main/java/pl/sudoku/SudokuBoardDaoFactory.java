package pl.sudoku;

public class SudokuBoardDaoFactory {
    public Dao<SudokuBoard> getFileDao(String file) {
        return new FileSudokuBoardDao(file);
    }
}
