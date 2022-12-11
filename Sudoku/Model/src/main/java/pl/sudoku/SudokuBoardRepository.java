package pl.sudoku;

<<<<<<< HEAD
public class SudokuBoardRepository implements Repository<SudokuBoard> {
    private final SudokuBoard sudokuBoard;

    public SudokuBoardRepository(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
=======

public class SudokuBoardRepository implements Repository<SudokuBoard> {
    private final SudokuBoard obj;

    public SudokuBoardRepository(SudokuBoard obj) {
        this.obj = obj;
>>>>>>> 9e138b33089c356a5466a028177ad5a72ab7f608
    }

    @Override
    public SudokuBoard createInstance() {
<<<<<<< HEAD
        return sudokuBoard.clone();
=======
        return obj.clone();
>>>>>>> 9e138b33089c356a5466a028177ad5a72ab7f608
    }
}
